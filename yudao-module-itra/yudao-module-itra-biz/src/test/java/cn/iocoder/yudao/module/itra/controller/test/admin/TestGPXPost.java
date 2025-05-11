//package cn.iocoder.yudao.module.itra.controller.test.admin;
//
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVPrinter;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartUtils;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//import org.locationtech.spatial4j.context.SpatialContext;
//import org.locationtech.spatial4j.distance.DistanceUtils;
//import org.locationtech.spatial4j.shape.Point;
//import me.himanshusoni.gpxparser.GPXParser;
//import me.himanshusoni.gpxparser.modal.GPX;
//import me.himanshusoni.gpxparser.modal.Track;
//import me.himanshusoni.gpxparser.modal.TrackSegment;
//import me.himanshusoni.gpxparser.modal.Waypoint;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.Duration;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.io.FileInputStream;
//
//public class TestGPXPost {
//    private static final String OUTPUT_DIR = "/Volumes/pipi/cursorProject/ruoyi-vue-pro/yudao-module-itra/yudao-module-itra-biz/src/test/resources/output/";
//
//    public static void main(String[] args) {
//        try {
//            // 创建输出目录
//            Files.createDirectories(Paths.get(OUTPUT_DIR));
//
//            // 读取GPX文件
//            GPXParser parser = new GPXParser();
//            FileInputStream in = new FileInputStream("/Volumes/pipi/cursorProject/ruoyi-vue-pro/yudao-module-itra/yudao-module-itra-biz/src/test/resources/蜀道山50轨迹+最终版.gpx");
//            GPX gpx = parser.parseGPX(in);
//
//            // 1. 基础数据分析
//            GpxAnalysisResult analysisResult = analyzeGpx(gpx);
//
//            // 2. 导出为JSON
//            exportToJson(analysisResult);
//
//            // 3. 导出为CSV
//            exportToCsv(analysisResult);
//
//            // 4. 生成图表
//            generateCharts(analysisResult);
//
//            // 5. 打印分析结果
//            printAnalysisResult(analysisResult);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static class GpxAnalysisResult {
//        // 基础信息
//        String name;
//        String description;
//        String author;
//        Duration totalDuration;
//        double totalDistance;
//        double totalElevationGain;
//
//        // 海拔信息
//        double maxElevation;
//        double minElevation;
//        List<ElevationPoint> elevationProfile;
//
//        // 速度信息
//        double maxSpeed;
//        double minSpeed;
//        double avgSpeed;
//
//        // 心率信息
//        List<HeartRatePoint> heartRateData;
//        double avgHeartRate;
//        double maxHeartRate;
//        double minHeartRate;
//
//        // 坡度信息
//        List<SlopePoint> slopeData;
//        double maxSlope;
//        double avgSlope;
//
//        // 天气信息
//        List<WeatherPoint> weatherData;
//
//        // CP点信息
//        List<CheckPoint> checkPoints;
//    }
//
//    private static class ElevationPoint {
//        double distance;
//        double elevation;
//        double slope;
//    }
//
//    private static class HeartRatePoint {
//        double distance;
//        int heartRate;
//    }
//
//    private static class SlopePoint {
//        double distance;
//        double slope;
//    }
//
//    private static class WeatherPoint {
//        double distance;
//        double temperature;
//        String conditions;
//    }
//
//    private static class CheckPoint {
//        String name;
//        double distance;
//        double elevation;
//        Duration time;
//    }
//
//    private static GpxAnalysisResult analyzeGpx(GPX gpx) {
//        GpxAnalysisResult result = new GpxAnalysisResult();
//        SpatialContext ctx = SpatialContext.GEO;
//
//        // 初始化结果对象
//        result.elevationProfile = new ArrayList<>();
//        result.heartRateData = new ArrayList<>();
//        result.slopeData = new ArrayList<>();
//        result.weatherData = new ArrayList<>();
//        result.checkPoints = new ArrayList<>();
//
//        // 设置元数据
//        if (gpx.getMetadata() != null) {
//            result.name = gpx.getMetadata().getName();
//            result.description = gpx.getMetadata().getDesc();
//            result.author = gpx.getMetadata().getAuthor();
//        }
//
//        double totalDistance = 0;
//        double totalElevationGain = 0;
//        double maxElevation = Double.MIN_VALUE;
//        double minElevation = Double.MAX_VALUE;
//        double maxSpeed = 0;
//        double minSpeed = Double.MAX_VALUE;
//        Duration totalDuration = Duration.ZERO;
//
//        // 心率数据统计
//        List<Integer> heartRates = new ArrayList<>();
//
//        // 遍历所有轨迹点
//        for (Track track : gpx.getTracks()) {
//            for (TrackSegment segment : track.getTrackSegments()) {
//                List<Waypoint> points = segment.getWaypoints();
//
//                for (int i = 1; i < points.size(); i++) {
//                    Waypoint prev = points.get(i - 1);
//                    Waypoint curr = points.get(i);
//
//                    // 计算距离
//                    Point p1 = ctx.getShapeFactory().pointLatLon(prev.getLatitude().doubleValue(), prev.getLongitude().doubleValue());
//                    Point p2 = ctx.getShapeFactory().pointLatLon(curr.getLatitude().doubleValue(), curr.getLongitude().doubleValue());
//                    double distance = ctx.getDistCalc().distance(p1, p2) * DistanceUtils.DEG_TO_KM;
//                    totalDistance += distance;
//
//                    // 计算海拔和坡度
//                    if (curr.getElevation() != null && prev.getElevation() != null) {
//                        double currElevation = curr.getElevation();
//                        double prevElevation = prev.getElevation();
//                        double elevationDiff = currElevation - prevElevation;
//
//                        maxElevation = Math.max(maxElevation, currElevation);
//                        minElevation = Math.min(minElevation, currElevation);
//
//                        if (elevationDiff > 0) {
//                            totalElevationGain += elevationDiff;
//                        }
//
//                        // 计算坡度
//                        double slope = (elevationDiff / distance) * 100; // 百分比坡度
//
//                        // 添加到海拔剖面数据
//                        ElevationPoint ep = new ElevationPoint();
//                        ep.distance = totalDistance;
//                        ep.elevation = currElevation;
//                        ep.slope = slope;
//                        result.elevationProfile.add(ep);
//
//                        // 添加到坡度数据
//                        SlopePoint sp = new SlopePoint();
//                        sp.distance = totalDistance;
//                        sp.slope = slope;
//                        result.slopeData.add(sp);
//                    }
//
//                    // 计算速度
//                    if (curr.getTime() != null && prev.getTime() != null) {
//                        long timeDiffMillis = curr.getTime().getTime() - prev.getTime().getTime();
//                        Duration timeDiff = Duration.ofMillis(timeDiffMillis);
//                        totalDuration = totalDuration.plus(timeDiff);
//
//                        if (timeDiff.toSeconds() > 0) {
//                            double speed = distance / (timeDiff.toSeconds() / 3600.0); // km/h
//                            maxSpeed = Math.max(maxSpeed, speed);
//                            minSpeed = Math.min(minSpeed, speed);
//                        }
//                    }
//
//                    // 收集心率数据
//                    curr.getExtensions().forEach(extension -> {
//                        extension.getElementsByTagName("gpxtpx:hr").forEach(hr -> {
//                            try {
//                                int heartRate = Integer.parseInt(hr.getTextContent());
//                                heartRates.add(heartRate);
//
//                                HeartRatePoint hrp = new HeartRatePoint();
//                                hrp.distance = totalDistance;
//                                hrp.heartRate = heartRate;
//                                result.heartRateData.add(hrp);
//                            } catch (NumberFormatException e) {
//                                // 忽略无效的心率数据
//                            }
//                        });
//                    });
//
//                    // 收集天气数据
//                    curr.getExtensions().forEach(extension -> {
//                        extension.getElementsByTagName("weather:temperature").forEach(temp -> {
//                            try {
//                                double temperature = Double.parseDouble(temp.getTextContent());
//                                WeatherPoint wp = new WeatherPoint();
//                                wp.distance = totalDistance;
//                                wp.temperature = temperature;
//                                wp.conditions = extension.getElementsByTagName("weather:conditions")
//                                        .stream()
//                                        .findFirst()
//                                        .map(e -> e.getTextContent())
//                                        .orElse("未知");
//                                result.weatherData.add(wp);
//                            } catch (NumberFormatException e) {
//                                // 忽略无效的天气数据
//                            }
//                        });
//                    });
//                }
//            }
//        }
//
//        // 处理CP点
//        for (Waypoint wayPoint : gpx.getWaypoints()) {
//            if (wayPoint.getName() != null) {
//                CheckPoint cp = new CheckPoint();
//                cp.name = wayPoint.getName();
//                cp.elevation = wayPoint.getElevation() != null ? wayPoint.getElevation() : 0.0;
//                cp.time = wayPoint.getTime();
//                result.checkPoints.add(cp);
//            }
//        }
//
//        // 设置结果
//        result.totalDistance = totalDistance;
//        result.totalElevationGain = totalElevationGain;
//        result.maxElevation = maxElevation;
//        result.minElevation = minElevation;
//        result.maxSpeed = maxSpeed;
//        result.minSpeed = minSpeed;
//        result.totalDuration = totalDuration;
//        result.avgSpeed = totalDistance / (totalDuration.toSeconds() / 3600.0);
//
//        // 计算心率统计
//        if (!heartRates.isEmpty()) {
//            result.avgHeartRate = heartRates.stream().mapToInt(Integer::intValue).average().orElse(0);
//            result.maxHeartRate = heartRates.stream().mapToInt(Integer::intValue).max().orElse(0);
//            result.minHeartRate = heartRates.stream().mapToInt(Integer::intValue).min().orElse(0);
//        }
//
//        // 计算坡度统计
//        if (!result.slopeData.isEmpty()) {
//            result.maxSlope = result.slopeData.stream().mapToDouble(p -> p.slope).max().orElse(0);
//            result.avgSlope = result.slopeData.stream().mapToDouble(p -> p.slope).average().orElse(0);
//        }
//
//        return result;
//    }
//
//    private static void exportToJson(GpxAnalysisResult result) throws IOException {
//        // 使用Jackson将结果转换为JSON
//        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
//        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
//        Files.write(Paths.get(OUTPUT_DIR + "analysis_result.json"), json.getBytes());
//    }
//
//    private static void exportToCsv(GpxAnalysisResult result) throws IOException {
//        // 导出海拔剖面数据
//        try (CSVPrinter printer = new CSVPrinter(new FileWriter(OUTPUT_DIR + "elevation_profile.csv"), CSVFormat.DEFAULT)) {
//            printer.printRecord("距离(km)", "海拔(m)", "坡度(%)");
//            for (ElevationPoint ep : result.elevationProfile) {
//                printer.printRecord(ep.distance, ep.elevation, ep.slope);
//            }
//        }
//
//        // 导出血率数据
//        if (!result.heartRateData.isEmpty()) {
//            try (CSVPrinter printer = new CSVPrinter(new FileWriter(OUTPUT_DIR + "heart_rate.csv"), CSVFormat.DEFAULT)) {
//                printer.printRecord("距离(km)", "心率(bpm)");
//                for (HeartRatePoint hrp : result.heartRateData) {
//                    printer.printRecord(hrp.distance, hrp.heartRate);
//                }
//            }
//        }
//    }
//
//    private static void generateCharts(GpxAnalysisResult result) throws IOException {
//        // 生成海拔剖面图
//        XYSeries elevationSeries = new XYSeries("海拔");
//        for (ElevationPoint ep : result.elevationProfile) {
//            elevationSeries.add(ep.distance, ep.elevation);
//        }
//
//        XYSeriesCollection dataset = new XYSeriesCollection();
//        dataset.addSeries(elevationSeries);
//
//        JFreeChart chart = ChartFactory.createXYLineChart(
//            "海拔剖面图",
//            "距离 (km)",
//            "海拔 (m)",
//            dataset
//        );
//
//        ChartUtils.saveChartAsPNG(
//            new java.io.File(OUTPUT_DIR + "elevation_profile.png"),
//            chart,
//            800,
//            600
//        );
//
//        // 生成心率图
//        if (!result.heartRateData.isEmpty()) {
//            XYSeries heartRateSeries = new XYSeries("心率");
//            for (HeartRatePoint hrp : result.heartRateData) {
//                heartRateSeries.add(hrp.distance, hrp.heartRate);
//            }
//
//            dataset = new XYSeriesCollection();
//            dataset.addSeries(heartRateSeries);
//
//            chart = ChartFactory.createXYLineChart(
//                "心率变化图",
//                "距离 (km)",
//                "心率 (bpm)",
//                dataset
//            );
//
//            ChartUtils.saveChartAsPNG(
//                new java.io.File(OUTPUT_DIR + "heart_rate.png"),
//                chart,
//                800,
//                600
//            );
//        }
//    }
//
//    private static void printAnalysisResult(GpxAnalysisResult result) {
//        System.out.println("=== 轨迹分析结果 ===");
//        System.out.println("名称: " + result.name);
//        System.out.println("描述: " + result.description);
//        System.out.println("作者: " + result.author);
//        System.out.println("总距离: " + String.format("%.2f", result.totalDistance) + " 公里");
//        System.out.println("总爬升: " + String.format("%.2f", result.totalElevationGain) + " 米");
//        System.out.println("总用时: " + formatDuration(result.totalDuration));
//        System.out.println("平均速度: " + String.format("%.2f", result.avgSpeed) + " km/h");
//        System.out.println("最高海拔: " + String.format("%.2f", result.maxElevation) + " 米");
//        System.out.println("最低海拔: " + String.format("%.2f", result.minElevation) + " 米");
//        System.out.println("最大坡度: " + String.format("%.2f", result.maxSlope) + "%");
//        System.out.println("平均坡度: " + String.format("%.2f", result.avgSlope) + "%");
//
//        if (result.avgHeartRate > 0) {
//            System.out.println("\n=== 心率统计 ===");
//            System.out.println("平均心率: " + String.format("%.1f", result.avgHeartRate) + " bpm");
//            System.out.println("最大心率: " + result.maxHeartRate + " bpm");
//            System.out.println("最小心率: " + result.minHeartRate + " bpm");
//        }
//
//        System.out.println("\n=== CP点信息 ===");
//        for (CheckPoint cp : result.checkPoints) {
//            System.out.println("CP点: " + cp.name);
//            System.out.println("海拔: " + String.format("%.2f", cp.elevation) + " 米");
//            if (cp.time != null) {
//                System.out.println("时间: " + cp.time);
//            }
//            System.out.println("----------------------------------------");
//        }
//
//        System.out.println("\n分析结果已导出到: " + OUTPUT_DIR);
//    }
//
//    private static String formatDuration(Duration duration) {
//        long hours = duration.toHours();
//        long minutes = duration.toMinutesPart();
//        long seconds = duration.toSecondsPart();
//        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
//    }
//}