package com.zhou.jdkdemo;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JdkDemo {

    @Data
    static class TestClass {
        private String hotelCode;
        private String rateCode;
        private List<String> channelCodes;
        private int onlineStatus;
        private String ruleCode;
    }

    private static Map<String, TestClass> resultMap;

    public static void aamain(String[] args) {

        List<TestClass> result = new ArrayList<>();
        List<TestClass> downResult = new ArrayList<>();
        resultMap = new HashMap<>();
        List<String> rateCodes = Arrays.asList("BDX0", "BDX1", "BDX2", "BDC0", "BDC1", "BDC2");

        String excelFilePath = "/Users/andy/Downloads/input.xlsx";
        String outputFilePath = "/Users/andy/Downloads/output_1.xlsx";
        String outputFilePath1 = "/Users/andy/Downloads/output_2.xlsx";
        List<String> hotelCodes = Arrays.asList("MTSXYC","MJCCJC");
        //List<String> hotelCodes = Arrays.asList("MJXSJC","MTUQDH","MJWKS","MJQZLY","MJZJYL","MJQDHU","MJXZGL","MJYALS","MJHZZD","MJZJAH","MHNBDC","MJJDZ","MJSQSY","MJCZSS","MJPHDH","MJJXHC","MJKSHQ","MJWCXD","MJZZHS","MJLYXG","MJHZLZ","MJHZNZ","MJSXLX","MJSLY","MJDLWD","MJNBTY","MJWHNL","MTUQTLJ","MTUHZGS","MJHZWD","MTUFAD","MJLIJA","MTUBL","MTUDQN","MJHZFT","MJNBWD","MJHUSH","MJNBHY","MJHZXD","MJDLXH","MJZSJC","MJZZGM","MJSXAC","MJHZMZ","MTUHANI","MJYICH","MJWYS","MJHNKT","MJHZLP","MJHZYP","MJHNSH","MJSXRM","MJNBCH","MJNTXGY","MTUHSBM","MJSXBH","MJJXNH","MHXSQC","MJSXSJ","MJNBFH","MJZZJ","MJTSXC","MJXNBW","MJNBGL","MJSXOP","MJLYJF","MTUNBDC","MJZSDH","MJZSSJM","MJSXPJ","MTWZDX","YJZSXC","MJSHJS","MTHZCJ","MTZSXC","MTHNYH","MTNBCH","MTUNINB","MJSHCXD","MTNBHS","MTUJHY","MTTZXJ","MTSHFX","MTTYCZ","MTTZWL","MTWZOJ","MTNBZH","MTWZLG","MTTXGJ","YJTYMX","MTXSSP","YJCXSA","YJDQGT","YJHZYH","YJPYTJ","YJSHHQ","YJSXHT","YJTZSM","YJYUYO","YJZJG","YJXZLLS","MJZSML","MTUKEQI","YJDQMX","YJHOHE","YJHZDQ","YJHZHB","YJHZSY","YJHZWD","YJJDMC","YJNBYQ","YJNINH","YJSXXC","YJTZSXJ","YJXINYU","YJZSDJD","MTWZRA","YJFCGLD","RBQZST","YJFUYN","MDHZZQ","MJXSDC","ATKSHQP","MJSHHB","MJXCDF","YJMGS","YJQDWH","YJHZLS","YJXSPP","YJJHSL","YJJHBL","KYHZQC","KYSXJC","YZFXHU","MTQDHU","ATKQZH","MTZJWD","MTLUSH","YZJNRJ","MTXSAP","MTGHW","MTWENZ","MJJDHK","KYAQHN","KYJXYL","MTSQSH","MTDQMG","MTLASC","KYZSPT","MTBFL","MTHZFK","MTDMW","MTJXDM","MTCXHZW","KYTSJT","MTHZNX","KYJSY","MTJDZ","MTYALN","MTTERZ","MTZJFQ","MTTALJ","MTSQZT","MTJDXAJ","KYYCYZ","MTNBXJD","MTNBKL","MTCIXI","KYSHLG","MJYCDF","YJSRSZ","YJJZYC","MTJJFL","KYYWRX","DJNHHY","KYWZYJ","KYSQSY","MTTZDF","MTSHSI","GTNXGZ","MTCZGC","KYNINB","MJWZXZ","MTHZLN","MTNNHZ","MTPDS","MTQAYG","MTHUZH","YJSQYG","MTHUSH","YJSZLD","YJHZDW","YJJXHBL","YJJDDX","KYKLKH","MJHGMC","MJFQSF","MTCQNA","MTHZZT","MTWLST","MRZZTEZ","MTWZLW","YJHADX","MTXABQ","YJLCFH","YJGYHZ","MTSJZZD","MTAQYJ","MTSZSK","RBSZSK","MJKQYT","MJHSTK","MTFZKCY","MJFCAQ","MJHZLLW","YJCZJHS","RBZHYF","YJHUZDW","MJWZLG","MTNBDF","MTPXWGS","MTSHBZ","YJWZYJ","FCHNYG","YJSXKY","GTCQDZ","KYTJRW","MTFZLA","KYHZFCD","MDHZXHBY","MDNBZH","FCDHZFC","FCDDDH","MTCXBY","MDHZBD","MDCHZH","MDYCXS","KYYCDZH","FCDFZZX","KYSXMD","KYZJMD","KYNHMD","KYBHMD","KYSLMD","MDHZCX","MDNMG","KYXXF","MTHZDW","MDHZBA","MDFCYT","DJNJGC","MDLSXSD","MDSZSX","MDXIAN","GTSRYT","KYSRSQS","KYHZXMS","MDZUNY","FCTZAM","GTYYYM","GTBBS","KYLZTY","MDLXG","YJWLKJ","YJCCGZ","MTFUYA","MTHGS","MDCZLY","FCDNJGC","KYSYMD","MDRUGO","MDRZDH","MDTAFHW","KYXZYQ","FCYZHE","MDFYDFM","DJBHYT","KYXSSP","KYTTTH","KYWZDT","MDYWSD","KYSHKJ","KYSCKX","GTXSGL","FCDZHJI","GTZSPT","GTWYWQ","MJWUZH","MTHZLP","MDWZPY","MTSZZZ","MTHZXS","MDHZDW","KYAGGJ","MDJJLX","KYLAJC","KYSMHU","KYLYNS","KYZAOZ","MTWUHU","MTJHSES","YJCXGL","MTYBJL","MJZJGM","MJZSJFL","MJCQSZF","MDXCGD","DJXZYM","MTJXYG","MDSXJH","MDHZQJW","MTCCSH","MTHZW","KYSHPD","MDJSSB","DJYBG","GTJXLX","KYAJZBY","MTCZFY","MTNACH","MDLCSD","MDYCZS","KYHYGJ","KYZMD","MDJNZQ","MDJUYE","MDWZTS","KYPUER","KYZSDS","KYXSDG","MTXHQ","KYWZPY","KYBHSH","MDHZOP","MDQDAO","MTHZYR","KYWHQS","MTFZLC","MTSXYC","MJCCJC","KYBJGH","MTHNPG","MTZHSH","MJHYWJW","MJTZSF","MTUPT","MTUZSHY","MJHZAG","MJHFBH","YJQDHU","YJQLS","YJPTDG","MJNBHQC","YJTAIX","MDNBZD","MTHFFH","MDYXCB","MJLHYT","MJSXKQ","MTHZQJ","MTSYSB");
        List<String> channelCodes = Arrays.asList("BDX", "CTRIP", "PTN", "TAOBAO", "IDS", "TMC", "TMCD");

        try {
            FileInputStream fis = new FileInputStream(excelFilePath);
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);
            Map<String, Integer> columnMap = getColumnMap(sheet);

            Workbook outputWorkbook = new XSSFWorkbook();
            Sheet outputSheet = outputWorkbook.createSheet();

            Workbook outputWorkbook1 = new XSSFWorkbook();
            Sheet outputSheet1 = outputWorkbook1.createSheet();

            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);

                String hotelCode = getCellValue(row.getCell(columnMap.get("hotel_code")));
                String channelCode = getCellValue(row.getCell(columnMap.get("channel_code")));
                String rateCode = getCellValue(row.getCell(columnMap.get("rate_code")));
                int onlineStatus = Integer.parseInt(getCellValue(row.getCell(columnMap.get("status"))));
                if (!hotelCodes.contains(hotelCode) || !channelCodes.contains(channelCode)) {
                    continue;
                }

                switch (rateCode) {
                    case "BDX0":
                    case "BDC0":
                    case "CARE0":
                        handleOnlineStatus(hotelCode, channelCode, "CWEB0", onlineStatus);
                        break;
                    case "BDX1":
                    case "BDC1":
                    case "CARE1":
                        handleOnlineStatus(hotelCode, channelCode, "CWEB1", onlineStatus);
                        break;
                    case "BDX2":
                    case "BDC2":
                    case "CARE2":
                        handleOnlineStatus(hotelCode, channelCode, "CWEB2", onlineStatus);
                        break;
                }

                if ("BDX".equals(channelCode) && rateCodes.contains(rateCode) && onlineStatus == 2) {
                    downBdxRate(hotelCode, rateCode, downResult);
                }
            }

            recordHead(outputSheet);
            int count = 1;
            for (TestClass value : resultMap.values()) {
                result.add(value);
                Row outputRow1 = outputSheet.createRow(count++);
                write(outputRow1, value);
            }

            recordHead(outputSheet1);
            int num = 1;
            for (TestClass value : downResult) {
                Row outputRow2 = outputSheet1.createRow(num++);
                write(outputRow2, value);
            }

            workbook.close();
            fis.close();

//            FileOutputStream fos = new FileOutputStream(outputFilePath);
//            outputWorkbook.write(fos);
//            outputWorkbook.close();
//            fos.close();
//
//            FileOutputStream fos1 = new FileOutputStream(outputFilePath1);
//            outputWorkbook1.write(fos1);
//            outputWorkbook1.close();
//            fos1.close();

//            List<String> dis = result.stream().map(TestClass::getHotelCode).distinct().collect(Collectors.toList());
//            for (String hotelCode : hotelCodes) {
//                if (!dis.contains(hotelCode)) {
//                    System.out.println(hotelCode);
//                }
//            }
            System.out.println(JSON.toJSON(result));
            //System.out.println(JSON.toJSON(downResult));

//            List<TestClass> reverseResult = myReverse(result);
//            List<TestClass> reverseDown = myReverse(downResult);
//            System.out.println(JSON.toJSON(reverseResult));
//            System.out.println(JSON.toJSON(reverseDown));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void recordHead(Sheet outputSheet) {
        Row outputRow = outputSheet.createRow(0);
        writeCellValue(outputRow.createCell(0), "hotel_code");
        writeCellValue(outputRow.createCell(1), "channel_code");
        writeCellValue(outputRow.createCell(2), "rate_code");
        writeCellValue(outputRow.createCell(3), "status");
    }

    private static void write(Row outputRow, TestClass value) {
        writeCellValue(outputRow.createCell(0), value.getHotelCode());
        writeCellValue(outputRow.createCell(1), value.getChannelCodes().get(0));
        writeCellValue(outputRow.createCell(2), value.getRateCode());
        writeCellValue(outputRow.createCell(3), String.valueOf(value.getOnlineStatus()));
    }

    private static void downBdxRate(String hotelCode, String rateCode, List<TestClass> downResult) {
        TestClass testClass = new TestClass();
        testClass.setHotelCode(hotelCode);
        testClass.setRateCode(rateCode);
        testClass.setOnlineStatus(3);
        testClass.setChannelCodes(Collections.singletonList("BDX"));
        downResult.add(testClass);
    }

    private static List<TestClass> myReverse(List<TestClass> list) {
        List<TestClass> result = new ArrayList<>();
        for (TestClass data : list) {
            TestClass testClass = new TestClass();
            testClass.setHotelCode(data.getHotelCode());
            testClass.setRateCode(data.getRateCode());
            testClass.setChannelCodes(data.getChannelCodes());
            if (data.getOnlineStatus() == 2) {
                testClass.setOnlineStatus(3);
            } else {
                testClass.setOnlineStatus(2);
                testClass.setRuleCode(getRuleCode(data.getChannelCodes().get(0)));
            }
            result.add(testClass);
        }
        return result;
    }

    private static void writeCellValue(Cell cell, String value) {
        if (cell != null) {
            cell.setCellValue(value);
        }
    }

    private static void handleOnlineStatus(String hotelCode, String channelCode, String rateCode, int onlineStatus) {
        TestClass mapData = resultMap.get(channelCode + hotelCode + rateCode);
        if (mapData != null && mapData.getOnlineStatus() == 2) {
            // 如果已经存在并且是需要上线 则不再重复处理
            return;
        }

        TestClass testClass = new TestClass();
        testClass.setHotelCode(hotelCode);
        testClass.setRateCode(rateCode);
        testClass.setChannelCodes(Collections.singletonList(channelCode));
        if (onlineStatus == 2) {
            testClass.setOnlineStatus(2);
            testClass.setRuleCode(getRuleCode(channelCode));
        } else {
            testClass.setOnlineStatus(3);
        }
        resultMap.put(channelCode + hotelCode + rateCode, testClass);
    }

    private static String getRuleCode(String channelCode) {
        switch (channelCode) {
            case "BDX":
                return "PFAC";
            case "CTRIP":
            case "PTN":
            case "TAOBAO":
            case "IDS":
                return "PA18";
            default:
                return "CONFIG1";
        }
    }

    private static Map<String, Integer> getColumnMap(Sheet sheet) {
        Map<String, Integer> columnMap = new HashMap<>();

        Row headerRow = sheet.getRow(0);
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            String columnValue = getCellValue(headerRow.getCell(i));
            columnMap.put(columnValue, i);
        }

        return columnMap;
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        } else {
            return "";
        }
    }
}

/**
 * @createTime: 2024-01-05 08:44
 * @description: jdkDemo类
 */
//@Component
//public class JdkDemo {
//    public static void main(String[] args) {
//        String str = "KYHZMD,KYNBMD,KYSHPD,KYNJDY,KYNINB,KYSXDY,KYQDHU,KYKFMD,MDJSSB,MTUPT,KYTJRW,MTZHSH,MTTALJ,KYHAMD,MTHZNX,MDHZDW,YJFCGLD,MJDLXH,KYGABDW,MJNBDC,MTJJDL,MJZSPT,MDCCHY,MJWUZH,KYZSDS,MJZSJC,MDHZBA,MJDLSF,FCYZHE,YJHSTX,MDHANI,YJZJG,KYSCKX,YJPTDG,MDLSXSD,MJXSAP,KYLYNS,KYHZXMS,AYSXLX,FCDNJGC,MTSQZT,ATKQZH,MDRASD,GTJXLXH,MJQZQJ,MTNACH,MJYCDF,MTHZCJ,MJHFBH,YJQLS,MJPHDH,MJCZSS,KYXZYQ,MTSHFX,MDWENZ,YJMGS,MDHZBJ,YJDQMX,GTXSGL,MTLASC,RBQZST,MTZJWD,MTHUSH,MJKQYT,MTPXWGS,RBZHYF,RBSZSK,YJGYHZ,MTHGS";
//        List<String> stringList = Arrays.asList(str.split(","));
//        List<TestClass> testList = new ArrayList<>();
//        for (String string : stringList) {
//            TestClass testClass1 = new TestClass();
//            testClass1.setHotelCode(string);
//            testClass1.setRateCode("CWEB0");
//            testClass1.setChannelCodes(Arrays.asList("PTN", "TAOBAO"));
//            testClass1.setOnlineStatus(3);
//            testClass1.setRuleCode("");
//            testList.add(testClass1);
//
//            TestClass testClass2 = new TestClass();
//            testClass2.setHotelCode(string);
//            testClass2.setRateCode("CWEB1");
//            testClass2.setChannelCodes(Arrays.asList("PTN", "TAOBAO"));
//            testClass2.setOnlineStatus(3);
//            testClass2.setRuleCode("");
//            testList.add(testClass2);
//
//            TestClass testClass3 = new TestClass();
//            testClass3.setHotelCode(string);
//            testClass3.setRateCode("CWEB2");
//            testClass3.setChannelCodes(Arrays.asList("PTN", "TAOBAO"));
//            testClass3.setOnlineStatus(3);
//            testClass3.setRuleCode("");
//            testList.add(testClass3);
//        }
//        System.out.println(JSON.toJSON(testList));
//    }


//    public static void main(String[] args) {
//        String str = "{\"hotelCode\":\"aaa\"}";
//        TestClass testClass = JSON.parseObject(str, TestClass.class);
//        System.out.println(testClass.getHotelCode());
//    }

//}
