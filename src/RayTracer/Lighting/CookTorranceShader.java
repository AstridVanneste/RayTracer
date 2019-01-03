package RayTracer.Lighting;

import RayTracer.Hit.HitObject;
import RayTracer.Hit.Ray;
import Math.Vector;
import Math.Geometry;
import Util.Color;

public class CookTorranceShader extends Shader
{
	private double intensity;
	private double m;
	private double eta[];
	private double kd;
	private double ks;

	public CookTorranceShader(double intensity, double m, double[] eta, double kd) throws IllegalArgumentException
	{
		if(eta.length != 3)
		{
			throw new IllegalArgumentException("not the right amount of eta values: " + eta.length);
		}

		this.m = m;
		this.eta = eta;
		this.kd = kd;
		this.ks = 1 - kd;
		this.intensity = intensity;
	}

	public CookTorranceShader(Material material)
	{
		this.m = material.getRoughness();
		this.eta = material.getRefractionIndex();
		this.kd = material.getKd();
		this.ks = 1.0 - this.kd;
		this.intensity = 1 - material.getReflectivity() - material.getRefractivity();
	}

	protected Color getLighterComponent(Light light, Ray ray, HitObject hit)
	{
		Vector viewDir = ray.getDir();
		viewDir = Vector.invert(viewDir);
		viewDir.normalize();

		Vector lightDir = Vector.subtract(light.getPosition(), hit.getHitpoint());
		lightDir.normalize();

		Vector halfway = this.calcHalfway(viewDir, lightDir);
		lightDir.normalize();

		Vector surfaceNormal = hit.getNormal();
		surfaceNormal.normalize();

		double phi = Geometry.angle(surfaceNormal, lightDir);
		double theta = Geometry.angle(surfaceNormal, viewDir);
		//double delta = this.calcDelta(phi, theta);
		double delta = Geometry.angle(surfaceNormal, halfway);

		//System.out.println("delta = " + delta);
		//System.out.println("diff  = " + (theta - phi));

		/*System.out.println("ANGLES");
		System.out.println("phi = " + phi);
		System.out.println("theta = " + theta);
		System.out.println("delta = " + delta);*/

		Color ambient = this.getAmbientComponent(light);
		Color diffuse = this.getDiffuseComponent(light, surfaceNormal, lightDir);
		Color specular = this.getSpecularComponent(light, phi, delta, surfaceNormal, halfway, viewDir, lightDir);


		//System.out.println(specular);

		Color lightColor = new Color(Color.BLACK);

		if(light.isEnableAmbient())
		{
			lightColor.add(ambient);
		}
		if(light.isEnableDiffuse())
		{
			lightColor.add(diffuse);
		}
		if(light.isEnableSpecular())
		{
			lightColor.add(specular);
		}

		Color hitColor = new Color(hit.getColor());
		hitColor.scale(lightColor);
		hitColor.scale(this.intensity);

		return hitColor;
	}


	private Vector calcHalfway(Vector viewDir, Vector lightDir)
	{
		return Vector.add(lightDir, viewDir);
	}

	private double calcDelta(double phi, double theta)
	{
		return (theta - phi)/2;
	}

	private double calcFresnelCoefficient(double phi, double eta)
	{
		double c = Math.cos(phi);
		double g = Math.sqrt(Math.pow(eta, 2) + Math.pow(c, 2) - 1);
		double fraction1 = 1.0/2.0 * (Math.pow(g - c, 2)/Math.pow(g + c, 2));
		double fraction2 = 1 + Math.pow((c * (g + c) - 1)/(c * (g - c) + 1), 2);

		return fraction1 * fraction2;
	}

	private double[] calcFresnelCoefficient(double phi)
	{
		double[] fresnel = new double[3];

		for(int i = 0; i < fresnel.length; i++)
		{
			fresnel[i] = this.calcFresnelCoefficient(phi, this.eta[i]);
		}

		return fresnel;
	}

	@Override
	protected Color getAmbientComponent(Light light)
	{
		Color ambient = new Color(light.getColor());

		double[] fresnel = this.calcFresnelCoefficient(0.0);
		ambient.scale(fresnel);
		ambient.scale(light.getAmbientStrength());

		return ambient;
	}

	private Color getDiffuseComponent(Light light, Vector surfaceNormal, Vector lightDir)
	{
		Color diffuse = new Color(light.getColor());
		diffuse.scale(this.kd);
		diffuse.scale(this.calcFresnelCoefficient(0.0));
		diffuse.scale(this.calcLambert(surfaceNormal, lightDir));

		return diffuse;
	}

	private double calcLambert(Vector surfaceNormal, Vector lightDir)
	{
		double lambert = Vector.dotProduct(lightDir, surfaceNormal)/(surfaceNormal.length() * lightDir.length());
		return Math.max(0.0, lambert);
	}

	private Color getSpecularComponent(Light light, double phi, double delta, Vector surfaceNormal, Vector halfway, Vector viewDir, Vector lightDir)
	{
		double[] spec = new double[3];

		for(int i = 0; i < spec.length; i++)
		{
			spec[i] = this.calcSpec(this.eta[i], phi, delta, surfaceNormal, halfway, viewDir, lightDir);
		}

		Color specular = new Color(light.getColor());
		specular.scale(this.ks);
		specular.scale(spec);

		return specular;
	}

	private double calcSpec(double eta, double phi, double delta, Vector surfaceNormal, Vector halfway, Vector viewDir, Vector lightDir)
	{
		double numerator = this.calcFresnelCoefficient(phi, eta) * this.calcDistribution(delta) * this.calcGeometryFactor(surfaceNormal, halfway, viewDir, lightDir);
		double denominator =  Vector.dotProduct(surfaceNormal, viewDir);

		return numerator/denominator;
	}

	private double calcGeometryFactor(Vector surfaceNormal, Vector halfway, Vector viewDir, Vector lightDir)
	{
		double Gm = this.calcMaskGeometryFactor(surfaceNormal, halfway, lightDir);
		double Gs = this.calcShadeGeometryFactor(surfaceNormal, halfway, viewDir, lightDir);

		return Math.min(Math.min(1.0, Gm), Gs);
	}

	private double calcShadeGeometryFactor(Vector surfaceNormal, Vector halfway, Vector viewDir, Vector lightDir)
	{
		double numerator = 2 * Vector.dotProduct(surfaceNormal, halfway) * Vector.dotProduct(surfaceNormal, viewDir);
		double denominator = Vector.dotProduct(halfway, lightDir);
		return numerator/denominator;
	}

	private double calcMaskGeometryFactor(Vector surfaceNormal, Vector halfway, Vector lightDir)
	{
		double numerator = 2 * Vector.dotProduct(surfaceNormal, halfway) * Vector.dotProduct(surfaceNormal, lightDir);
		double denominator = Vector.dotProduct(halfway, lightDir);

		return numerator/denominator;
	}

	private double calcDistribution(double delta)
	{
		double fraction = 1.0/(4.0 * Math.pow(this.m, 2.0) * Math.pow(Math.cos(delta), 4.0));
		double exponent = - (Math.pow(Math.tan(delta)/this.m, 2.0));
		double exp = Math.exp(exponent);

		double D = fraction * exp;
		//System.out.println(D);

		return D;
	}
}
