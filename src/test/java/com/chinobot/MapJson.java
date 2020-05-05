package com.chinobot;

import com.chinobot.common.utils.GPSUtil;
//import com.chinobot.common.utils.Geo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.awt.geom.Point2D;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;

public class MapJson {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //File file = new File("D:\\workspace\\plep\\src\\test\\resources\\jd.json");
        ClassPathResource resource = new ClassPathResource("sq.json");
        
        JsonNode jsonNode = mapper.readTree(resource.getFile());

        JsonNode features = jsonNode.get("features");
        //System.out.println(features);

        ObjectNode objNode = mapper.createObjectNode();
        objNode.put("type","FeatureCollection");
        ArrayNode arrNode = mapper.createArrayNode();
        for (JsonNode node: features) {
        	if(!"440306023".equals(node.get("attributes").get("JDBM").asText())) {
        		//System.out.println(node.get("attributes").get("SQMC").asText());
        		String fileName = node.get("attributes").get("SQMC").asText();
        		JsonNode lnglats = node.get("geometry").get("rings").get(0);
        		String rs = "";
        		for(JsonNode jn : lnglats) {
        			String lng = jn.get(0).asText();
        			String lat = jn.get(1).asText();
//        			Geo g = new Geo();
//        			Point2D.Double p = new Point2D.Double(Double.parseDouble(lng), Double.parseDouble(lat));
//        			p = g.toWGS84(p.x, p.y);
//        			double[] gps84_To_Gcj02 = GPSUtil.gps84_To_Gcj02(p.y,p.x);
        			double[] gps84_To_Gcj02 = GPSUtil.gps84_To_Gcj02(Double.parseDouble(lat),Double.parseDouble(lng));
        			String lnglat = gps84_To_Gcj02[1]+","+gps84_To_Gcj02[0];
        			rs += lnglat + ",";
        		}
        		rs = rs.substring(0, rs.length()-1);
        		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("D:\\workProject\\city\\workDoc\\zhsq\\" + fileName + ".txt"));
        		out.write(rs.getBytes());
        		//清楚缓存
                out.flush();
                out.close();
        		//System.out.println(rs);
        		//System.out.println("11");
        	}
//            ObjectNode subNode = mapper.createObjectNode();
//            subNode.put("id",node.get("attributes").get("JDBM").asText());
//            subNode.put("type","Feature");
//            ObjectNode subNode1 = mapper.createObjectNode();
//            subNode1.put("type", "Polygon");
//            subNode1.set("coordinates",node.get("geometry").get("rings"));
//            subNode.set("geometry",subNode1);
//
//            ObjectNode subNode2 = mapper.createObjectNode();
//            subNode2.put("name",node.get("attributes").get("JDMC").asText());
//            ArrayNode subArrayNode = mapper.createArrayNode();
//            subArrayNode.add(node.get("attributes").get("LON").asDouble());
//            subArrayNode.add(node.get("attributes").get("LAT").asDouble());
//            subNode2.set("cp", subArrayNode);
//            subNode2.put("childNum",1);
//            subNode.set("properties",subNode2);
//
//
//            arrNode.add(subNode);
        }

//        objNode.set("features",arrNode);
//        System.out.println(objNode);
    }
}
