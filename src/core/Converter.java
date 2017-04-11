package core;

import java.text.DecimalFormat;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

@Path("/")
public class Converter {
	
	private DecimalFormat df = new DecimalFormat("####0.00");
	
	private JSONObject json = new JSONObject();
	private JSONObject json_array = new JSONObject();
	private JSONObject json_array_arrays = new JSONObject();
	private JSONArray array = new JSONArray();

	@GET
	@Path("length/imperial/jsonobject")
	@Produces("application/json")
	public Response convertInToCmfromInputJSON(@DefaultValue("1") @QueryParam("in>>cm") Double input) {
		Double inches = input;
		Double centimeter = inches * 2.54;
		String in = df.format(inches).toString();
		String cm = df.format(centimeter).toString();

		json.put("calc", json_array);
		json_array.put("conversion", "inches to centimeters");
		json_array.put("inches", in);
		json_array.put("centimeter", cm);

		String out = json.toString();
		return Response.status(200).entity(out).build();
	}

	@GET
	@Path("length/metric/jsonarray")
	@Produces("application/json")
	public Response convertCmToInfromInputJSON(@DefaultValue("1") @QueryParam("cm>>in") Double input) {
		Double centimeter = input;
		Double inches = centimeter * 0.3937;
		String in = df.format(inches).toString();
		String cm = df.format(centimeter).toString();

		json.put("calc", json_array);
		json_array.put("conversion", "centimeters to inches");
		json_array.put("centimeter", cm);
		json_array.put("inches", in);

		json_array_arrays.put("weight", "lb to kg");
		json_array_arrays.put("temperature", "C to F");
		array.put(json_array_arrays);

		json_array.put("other conversions", array);
		String out = json.toString();

		return Response.status(200).entity(out).build();
	}

	@GET
	@Path("weight/imperial/jsonbuilder")
	@Produces("application/json")
	public Response convertLbToKgfromInputJSON(@DefaultValue("1") @QueryParam("lb>>kg") Double input) {
		Double pound = input;
		Double kilogram = pound * 0.4536;
		String lb = df.format(pound).toString();
		String kg = df.format(kilogram).toString();

		JsonBuilderFactory factory = Json.createBuilderFactory(null);
		JsonObject json = factory.createObjectBuilder()
				.add("calc",
						factory.createObjectBuilder() // adding root element
								.add("conversion", "pounds to kilograms ") // adding object
								.add("pound", lb) // adding object
								.add("kilogram", kg) // adding object
								)
				.add("other conversions",
						factory.createArrayBuilder() // adding array
								.add(factory.createObjectBuilder() // adding object
								.add("length", "in to cm") // adding object of array
								.add("temperature", "F to C")) // adding object of array
								)
				.build();
		
		String out = json.toString();
		return Response.status(200).entity(out).build();
	}

	@GET
	@Path("weight/metric/jsonbuilder")
	@Produces("application/json")
	public Response convertKgToLbfromInputJSON(@DefaultValue("1") @QueryParam("kg>>lb") Double input) {
		Double kilogram = input;
		Double pound = kilogram * 2.2046;
		String lb = df.format(pound).toString();
		String kg = df.format(kilogram).toString();
		
		JsonBuilderFactory factory = Json.createBuilderFactory(null);
		JsonObject json = factory.createObjectBuilder()
				.add("calc",
						factory.createObjectBuilder()
								.add("conversion", "kilograms to pounds") 
								.add("kilogram", kg)
								.add("pound", lb)
								)
				.add("other conversions",
						factory.createArrayBuilder()
								.add(factory.createObjectBuilder()
								.add("length", "cm to in")
								.add("temperature", "C to F"))
								)
				.build();
		
		String out = json.toString();
		return Response.status(200).entity(out).build();
	}
}