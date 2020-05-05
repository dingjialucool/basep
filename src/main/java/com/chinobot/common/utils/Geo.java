/*
 * package com.chinobot.common.utils;
 * 
 * import org.geotools.geometry.jts.JTS; import org.geotools.referencing.CRS;
 * import org.opengis.geometry.MismatchedDimensionException; import
 * org.opengis.referencing.crs.CoordinateReferenceSystem; import
 * org.opengis.referencing.operation.MathTransform; import
 * org.opengis.referencing.operation.TransformException; import
 * org.opengis.referencing.FactoryException;
 * 
 * import com.vividsolutions.jts.geom.Point; import
 * com.vividsolutions.jts.io.ParseException; import
 * com.vividsolutions.jts.io.WKTReader; import java.awt.geom.Point2D; import
 * org.geotools.referencing.crs.DefaultGeographicCRS;
 * 
 * public class Geo {
 * 
 * private static WKTReader reader;
 * 
 * private static CoordinateReferenceSystem crsWGS84; //WGS1984 private static
 * CoordinateReferenceSystem crsCGCS2000; //CGCS2000 中国大地坐标系
 * 
 * private static MathTransform wgs84toCgcs2000; //从WGS1984到CGCS2000 private
 * static MathTransform cgcs2000toWgs84; //从CGCS2000到WGS1984
 * 
 * //在oralce 数据库中的坐标系编号 //public static final int System_Ref = 4; public static
 * void main(String[] args) { Geo g = new Geo(); long startTime =
 * System.currentTimeMillis(); //获取开始时间 Point2D.Double p = new
 * Point2D.Double(104.3838501, 31.15288273);
 * System.out.printf("x: %7.11f y: %7.11f\r\n", p.x, p.y); p = g.toCGCS2000(p.x,
 * p.y); System.out.printf("x: %7.11f y: %7.11f\r\n", p.x, p.y); p =
 * g.toWGS84(p.x, p.y); System.out.printf("x: %7.11f y: %7.11f\r\n", p.x, p.y);
 * long endTime = System.currentTimeMillis(); //获取结束时间
 * System.out.println("程序运行时间： " + (endTime - startTime) + "ms"); }
 * 
 *//**
	 * 初始化坐标系信息，只需调用一次
	 */
/*
 * public static void init() { try { //初始化WKTReader reader = new
 * WKTReader();//new GeometryFactory() //初始化坐标转换类 //String
 * wktWgs1984="GEOGCS[\"WGS 84\", DATUM[\"World Geodetic System 1984\", SPHEROID[\"WGS 84\", 6378137.0, 298.257223563, AUTHORITY[\"EPSG\",\"7030\"]], AUTHORITY[\"EPSG\",\"6326\"]], PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]], UNIT[\"degree\", 0.017453292519943295], AXIS[\"Geodetic longitude\", EAST], AXIS[\"Geodetic latitude\", NORTH], AUTHORITY[\"EPSG\",\"4326\"]]"
 * ; String wktCgcs2000 =
 * "PROJCS[\"CGCS2000 / Gauss-Kruger CM 105E\",GEOGCS[\"China Geodetic Coordinate System 2000\",DATUM[\"China_2000\",SPHEROID[\"CGCS2000\",6378137,298.257222101,AUTHORITY[\"EPSG\",\"1024\"]],AUTHORITY[\"EPSG\",\"1043\"]],PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],UNIT[\"degree\",0.0174532925199433,AUTHORITY[\"EPSG\",\"9122\"]],AUTHORITY[\"EPSG\",\"4490\"]],UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],PROJECTION[\"Transverse_Mercator\"],PARAMETER[\"latitude_of_origin\",0],PARAMETER[\"central_meridian\",105],PARAMETER[\"scale_factor\",1],PARAMETER[\"false_easting\",500000],PARAMETER[\"false_northing\",0],AUTHORITY[\"EPSG\",\"4507\"],AXIS[\"X\",NORTH],AXIS[\"Y\",EAST]]"
 * ; crsCGCS2000 = CRS.parseWKT(wktCgcs2000); crsWGS84 =
 * DefaultGeographicCRS.WGS84; wgs84toCgcs2000 = CRS.findMathTransform(crsWGS84,
 * crsCGCS2000, true); cgcs2000toWgs84 = CRS.findMathTransform(crsCGCS2000,
 * crsWGS84, true);
 * 
 * } catch (FactoryException e) { e.printStackTrace(); } }
 * 
 * public Geo() { if (reader == null) { init(); } }
 * 
 * private Point2D.Double transform(double x, double y, MathTransform mt) { try
 * { Point point = (Point) reader.read("POINT (" + x + " " + y + ")"); Point
 * pGeo = (Point) JTS.transform(point, mt); return new
 * java.awt.Point.Double(pGeo.getX(), pGeo.getY()); } catch (ParseException |
 * MismatchedDimensionException | TransformException e) { e.printStackTrace(); }
 * return null; }
 * 
 *//**
	 * 将中国大地2000坐标转换为WGS1984
	 *
	 * @param x
	 * @param y
	 * @return
	 */
/*
 * public Point2D.Double toWGS84(double x, double y) { return transform(y, x,
 * cgcs2000toWgs84); }
 * 
 *//**
	 * 将WGS1984点坐标转换为中国大地2000坐标
	 *
	 * @param x 经度
	 * @param y 纬度
	 * @return
	 *//*
		 * public Point2D.Double toCGCS2000(double x, double y) { Point2D.Double point =
		 * transform(x, y, wgs84toCgcs2000); point.setLocation(point.y, point.x); return
		 * point; } }
		 */