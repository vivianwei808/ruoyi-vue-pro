package cn.iocoder.yudao.module.itra.controller.test.admin;

import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.distance.DistanceUtils;
import org.locationtech.spatial4j.shape.Point;
import me.himanshusoni.gpxparser.GPXParser;
import me.himanshusoni.gpxparser.modal.GPX;
import me.himanshusoni.gpxparser.modal.Track;
import me.himanshusoni.gpxparser.modal.TrackSegment;
import me.himanshusoni.gpxparser.modal.Waypoint;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.List;

public class TestGPXPre {
    public static void main(String[] args) {
        try {
            // 读取GPX文件
            GPXParser parser = new GPXParser();
            FileInputStream in = new FileInputStream("/Volumes/pipi/ideaProject/ruoyi-vue-pro/yudao-module-itra/yudao-module-itra-biz/src/test/resources/蜀道山50轨迹+最终版.gpx");
            GPX gpx = parser.parseGPX(in);
            
            // 打印元数据信息
            System.out.println("=== 元数据信息 ===");
            if (gpx.getMetadata() != null) {
                System.out.println("轨迹名称: " + (gpx.getMetadata().getName() != null ? gpx.getMetadata().getName() : "未命名"));
                System.out.println("描述: " + (gpx.getMetadata().getDesc() != null ? gpx.getMetadata().getDesc() : "无描述"));
                System.out.println("作者: " + (gpx.getMetadata().getAuthor() != null ? gpx.getMetadata().getAuthor().getName() : "未知"));
                System.out.println("创建时间: " + gpx.getMetadata().getTime());
                System.out.println("----------------------------------------");
            }

            // 创建空间上下文
            SpatialContext ctx = SpatialContext.GEO;
            
            // 存储所有轨迹点和CP点
            List<Waypoint> allPoints = new ArrayList<>();
            List<Waypoint> cpPoints = new ArrayList<>();
            double totalDistance = 0;
            double totalElevationGain = 0;
            double maxElevation = Double.MIN_VALUE;
            double minElevation = Double.MAX_VALUE;
            
            // 收集所有CP点
            for (Waypoint wayPoint : gpx.getWaypoints()) {
                if (wayPoint.getName() != null) {
                    cpPoints.add(wayPoint);
                }
            }
            
            // 遍历所有轨迹段
            for (Track track : gpx.getTracks()) {
                for (TrackSegment segment : track.getTrackSegments()) {
                    List<Waypoint> points = segment.getWaypoints();
                    allPoints.addAll(points);
                    
                    // 计算该段的距离和爬升
                    for (int i = 1; i < points.size(); i++) {
                        Waypoint prev = points.get(i - 1);
                        Waypoint curr = points.get(i);
                        
                        // 计算距离
                        Point p1 = ctx.getShapeFactory().pointLatLon(prev.getLatitude(), prev.getLongitude());
                        Point p2 = ctx.getShapeFactory().pointLatLon(curr.getLatitude(), curr.getLongitude());
                        double distance = ctx.getDistCalc().distance(p1, p2) * DistanceUtils.DEG_TO_KM;
                        totalDistance += distance;
                        
                        // 计算爬升
                        double currElevation = curr.getElevation();
                        double prevElevation = prev.getElevation();
                        double elevationDiff = currElevation - prevElevation;
                        
                        // 更新最大最小海拔
                        maxElevation = Math.max(maxElevation, currElevation);
                        minElevation = Math.min(minElevation, currElevation);
                        
                        if (elevationDiff > 0) {
                            totalElevationGain += elevationDiff;
                        }
                    }
                }
            }
            
            // 打印总体信息
            System.out.println("\n=== 轨迹信息 ===");
            System.out.println("总距离: " + String.format("%.2f", totalDistance) + " 公里");
            System.out.println("总爬升: " + String.format("%.2f", totalElevationGain) + " 米");
            System.out.println("最高海拔: " + String.format("%.2f", maxElevation) + " 米");
            System.out.println("最低海拔: " + String.format("%.2f", minElevation) + " 米");
            System.out.println("----------------------------------------");
            
            // 打印CP点信息及距离
            System.out.println("\n=== CP点信息 ===");
            for (int i = 0; i < cpPoints.size(); i++) {
                Waypoint wayPoint = cpPoints.get(i);
                System.out.println("CP" + (i + 1) + ": " + wayPoint.getName());
                System.out.println("位置: " + wayPoint.getLatitude() + ", " + wayPoint.getLongitude());
                System.out.println("海拔: " + String.format("%.2f", wayPoint.getElevation()) + " 米");
                
                // 计算到下一个CP点的距离和爬升
                if (i < cpPoints.size() - 1) {
                    Waypoint nextCP = cpPoints.get(i + 1);
                    Point currentPoint = ctx.getShapeFactory().pointLatLon(wayPoint.getLatitude(), wayPoint.getLongitude());
                    Point nextPoint = ctx.getShapeFactory().pointLatLon(nextCP.getLatitude(), nextCP.getLongitude());
                    double distanceToNext = ctx.getDistCalc().distance(currentPoint, nextPoint) * DistanceUtils.DEG_TO_KM;
                    double elevationDiff = nextCP.getElevation() - wayPoint.getElevation();
                    System.out.println("距离: " + String.format("%.2f", distanceToNext) + " 公里");
                    if (elevationDiff > 0) {
                        System.out.println("爬升: " + String.format("%.2f", elevationDiff) + " 米");
                    } else {
                        System.out.println("下降: " + String.format("%.2f", -elevationDiff) + " 米");
                    }
                }
                
                if (wayPoint.getDescription() != null) {
                    System.out.println("描述: " + wayPoint.getDescription());
                }
                System.out.println("----------------------------------------");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
