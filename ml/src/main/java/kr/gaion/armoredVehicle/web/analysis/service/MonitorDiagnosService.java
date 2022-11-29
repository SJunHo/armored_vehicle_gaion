package kr.gaion.armoredVehicle.web.analysis.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import kr.gaion.armoredVehicle.web.analysis.mapper.*;
import kr.gaion.armoredVehicle.web.analysis.model.*;
import kr.gaion.armoredVehicle.web.utils.Paging;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MonitorDiagnosService {
	
	@Autowired
	SdaMapper sdaMapper;
	
	@Autowired
	BerdataMapper berdataMapper;
	
	@Autowired
	EngdataMapper engdataMapper;
	
	@Autowired
	WhldataMapper whldataMapper;
	
	@Autowired
	GrbdataMapper grbdataMapper;
	
	@Autowired
	ParamdescMapper paramdescMapper;
	
	public List<Sda> getAllVehicleInfo() {
		List<Sda> sda = sdaMapper.findSda(null);
		return sda;
	}

	public Map<String, Object> getTroubleData(troubleDataRequest data){
		changeDate(data);
		Map<String, Object> map = new HashMap<String, Object>();
		String partName = data.getPart();

		Paging paging = new Paging();
		int page = data.getPage();
		int pageSize = data.getSize();
		String sdaid = data.getSdaid().toUpperCase();
		
		switch(partName) {
			case "BEARING":
				paging.setTotalcount(berdataMapper.countBerdataByTable(data));
				paging.setPagenum(page -1);
				paging.setContentnum(pageSize);
				paging.setCurrentblock(paging.getTotalcount());
				
				paging.prevnext(page);
				paging.setStartPage(paging.getCurrentblock());
				paging.setEndPage(paging.getLastblock(), paging.getCurrentblock());
				paging.setTotalPageCount();
				
				data.setPage(paging.getPagenum()*pageSize);
				data.setSize(paging.getContentnum());
				
				List<Berdata> troubleBerData = berdataMapper.findBerdata(data);
				
				map.put("troubleList", troubleBerData);
				map.put("paging", paging);
				for(Berdata b: troubleBerData) {
					System.out.println(b.getDate());
				}
				break;
			
			case "ENGINE":
				paging.setTotalcount(engdataMapper.countEngdataByTable(data));
				paging.setPagenum(page -1);
				paging.setContentnum(pageSize);
				paging.setCurrentblock(paging.getTotalcount());
				
				paging.prevnext(page);
				paging.setStartPage(paging.getCurrentblock());
				paging.setEndPage(paging.getLastblock(), paging.getCurrentblock());
				paging.setTotalPageCount();
				
				data.setPage(paging.getPagenum()*pageSize);
				data.setSize(paging.getContentnum());
				
				List<Engdata> troubleEngData = engdataMapper.findEngdata(data);
				
				map.put("troubleList", troubleEngData);
				map.put("paging", paging);
				System.out.println(map);
				break;
			
			case "GEARBOX":
				paging.setTotalcount(grbdataMapper.countGrbdataByTable(data));
				paging.setPagenum(page -1);
				paging.setContentnum(pageSize);
				paging.setCurrentblock(paging.getTotalcount());
				
				paging.prevnext(page);
				paging.setStartPage(paging.getCurrentblock());
				paging.setEndPage(paging.getLastblock(), paging.getCurrentblock());
				paging.setTotalPageCount();
				
				data.setPage(paging.getPagenum()*pageSize);
				data.setSize(paging.getContentnum());
				
				List<Grbdata> troubleGrbData = grbdataMapper.findGrbdata(data);
				
				map.put("troubleList", troubleGrbData);
				map.put("paging", paging);
				System.out.println(map);
				break;
				
			case "WHEEL":
				paging.setTotalcount(whldataMapper.countWhldataByTable(data));
				paging.setPagenum(page -1);
				paging.setContentnum(pageSize);
				paging.setCurrentblock(paging.getTotalcount());
				
				paging.prevnext(page);
				paging.setStartPage(paging.getCurrentblock());
				paging.setEndPage(paging.getLastblock(), paging.getCurrentblock());
				paging.setTotalPageCount();
				
				data.setPage(paging.getPagenum()*pageSize);
				data.setSize(paging.getContentnum());
				
				List<Whldata> troubleWhlData = whldataMapper.findWhldata(data);
				
				map.put("troubleList", troubleWhlData);
				map.put("paging", paging);
				System.out.println(map);
				break;

			case "SIMULATION":
				paging.setTotalcount(berdataMapper.countSimulationByTable(data));
				paging.setPagenum(page -1);
				paging.setContentnum(pageSize);
				paging.setCurrentblock(paging.getTotalcount());

				paging.prevnext(page);
				paging.setStartPage(paging.getCurrentblock());
				paging.setEndPage(paging.getLastblock(), paging.getCurrentblock());
				paging.setTotalPageCount();

				data.setPage(paging.getPagenum()*pageSize);
				data.setSize(paging.getContentnum());

				List<Berdata> simulationData = berdataMapper.findSimulation(data);

				map.put("troubleList", simulationData);
				map.put("paging", paging);

				break;
		}
		

		return map;
	}
	
	public void changeDate(troubleDataRequest data) {
		String startDate = data.getStartDate();
		if(startDate != null){
			startDate = startDate.substring(0, 10);
			startDate = startDate + " 00:00:00";
			data.setStartDate(startDate);
		}
		String endDate = data.getEndDate();
		if(endDate != null){
			endDate = endDate.substring(0, 10);
			endDate = endDate + " 23:59:59";
			data.setEndDate(endDate);
		}
	}
	
	public void downloadExcelTroubleData(HttpServletResponse response,
										ExcelDownByMonitorDiagnos data) {
		Workbook wb = new XSSFWorkbook();
		
		String startDate = data.getStartDate();
		startDate = startDate.substring(0, 10);
		startDate = startDate + " 00:00:00";
		data.setStartDate(startDate);
		
		String endDate = data.getEndDate();
		endDate = endDate.substring(0, 10);
		endDate = endDate + " 23:59:59";
		data.setEndDate(endDate);
		
		String partName = data.getPart().toString();
		switch(partName) {
		case "BEARING":
			wb = createExcelBerdata(data, wb);
			break;
		case "ENGINE":
			wb = createExcelEngdata(data, wb);
			break;
		case "GEARBOX":
			wb = createExcelGrbdata(data, wb);
			break;
		case "WHEEL":
			wb = createExcelWhldata(data, wb);
		}
		
		try {
			wb.write(response.getOutputStream());
			wb.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public Workbook createExcelBerdata(ExcelDownByMonitorDiagnos data, Workbook wb) {
		List<Berdata> resultList = berdataMapper.findBerdataForExcel(data);

		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		int cellNum = 49;
		Sheet sheet = wb.createSheet("베어링고장진단데이터");

		
		//header
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("좌/외륜");
		cell = row.createCell(1);
		cell.setCellValue("좌/내륜");
		cell = row.createCell(2);
		cell.setCellValue("좌/볼");
		cell = row.createCell(3);
		cell.setCellValue("좌/리테이너");
		cell = row.createCell(4);
		cell.setCellValue("우/외륜");
		cell = row.createCell(5);
		cell.setCellValue("우/내륜");
		cell = row.createCell(6);
		cell.setCellValue("우/볼");
		cell = row.createCell(7);
		cell.setCellValue("우/리테이너");
		cell = row.createCell(8);
		cell.setCellValue("차량이름");
		cell = row.createCell(9);
		cell.setCellValue("시점종합");
		cell = row.createCell(10);
		cell.setCellValue("W_RPM");
		cell = row.createCell(11);
		cell.setCellValue("L_B_V_OverallRMS");
		cell = row.createCell(12);
		cell.setCellValue("L_B_V_1X");
		
		cell = row.createCell(13);
		cell.setCellValue("L_B_V_6912BPFO");
		cell = row.createCell(14);
		cell.setCellValue("L_B_V_6912BPFI");
		cell = row.createCell(15);
		cell.setCellValue("L_B_V_6912BSF");
		cell = row.createCell(16);
		cell.setCellValue("L_B_V_6912FTF");
		cell = row.createCell(17);
		cell.setCellValue("L_B_V_32924BPFO");
		cell = row.createCell(18);
		cell.setCellValue("L_B_V_32924BPFI");
		
		cell = row.createCell(19);
		cell.setCellValue("L_B_V_32924BSF");
		cell = row.createCell(20);
		cell.setCellValue("L_B_V_32924FTF");
		cell = row.createCell(21);
		cell.setCellValue("L_B_V_32922BPFO");
		cell = row.createCell(22);
		cell.setCellValue("L_B_V_32922BPFI");
		cell = row.createCell(23);
		cell.setCellValue("L_B_V_32922BSF");
		cell = row.createCell(24);
		cell.setCellValue("L_B_V_32922FTF");
		
		cell = row.createCell(25);
		cell.setCellValue("L_B_V_Crestfactor");
		cell = row.createCell(26);
		cell.setCellValue("L_B_V_Demodulation");
		cell = row.createCell(27);
		cell.setCellValue("L_B_S_Fault1");
		cell = row.createCell(28);
		cell.setCellValue("L_B_S_Fault2");
		cell = row.createCell(29);
		cell.setCellValue("L_B_T_Temperature");
		
		cell = row.createCell(30);
		cell.setCellValue("R_B_V_OverallRMS");
		cell = row.createCell(31);
		cell.setCellValue("R_B_V_1X");
		cell = row.createCell(32);
		cell.setCellValue("R_B_V_6912BPFO");
		cell = row.createCell(33);
		cell.setCellValue("R_B_V_6912BPFI");
		cell = row.createCell(34);
		cell.setCellValue("R_B_V_6912BSF");
		cell = row.createCell(35);
		cell.setCellValue("R_B_V_6912FTF");
		
		cell = row.createCell(36);
		cell.setCellValue("R_B_V_32924BPFO");
		cell = row.createCell(37);
		cell.setCellValue("R_B_V_32924BPFI");
		cell = row.createCell(38);
		cell.setCellValue("R_B_V_32924BSF");
		cell = row.createCell(39);
		cell.setCellValue("R_B_V_32924FTF");
		cell = row.createCell(40);
		cell.setCellValue("R_B_V_32922BPFO");
		
		cell = row.createCell(41);
		cell.setCellValue("R_B_V_32922BPFI");
		cell = row.createCell(42);
		cell.setCellValue("R_B_V_32922BSF");
		cell = row.createCell(43);
		cell.setCellValue("R_B_V_32922FTF");
		cell = row.createCell(44);
		cell.setCellValue("R_B_V_Crestfactor");
		cell = row.createCell(45);
		cell.setCellValue("R_B_V_Demodulation");
		
		cell = row.createCell(46);
		cell.setCellValue("R_B_S_Fault1");
		cell = row.createCell(47);
		cell.setCellValue("R_B_S_Fault2");
		cell = row.createCell(48);
		cell.setCellValue("R_B_T_Temperature");
		cell = row.createCell(49);
		cell.setCellValue("AC_h");
		
		cell = row.createCell(50);
		cell.setCellValue("AC_v");
		cell = row.createCell(51);
		cell.setCellValue("AC_a");
		cell = row.createCell(52);
		cell.setCellValue("FILENM");
		
		//body
		for(Berdata s : resultList) {
			cellNum = 0;
			row = sheet.createRow(rowNum++);
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAI_LBPFO());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAI_LBPFI());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAI_LBSF());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAI_LFTF());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAI_RBPFO());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAI_RBPFI());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAI_RBSF());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAI_RFTF());
			
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getSdanm());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getDate());

			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getW_RPM());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_V_OverallRMS());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_V_1X());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_V_6912BPFO());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_V_6912BPFI());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_V_6912BSF());
			
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_V_6912FTF());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_V_32924BPFO());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_V_32924BPFI());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_V_32924BSF());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_V_32924FTF());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_V_32922BPFO());
			
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_V_32922BPFI());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_V_32922BSF());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_V_32922FTF());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_V_Crestfactor());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_V_Demodulation());
			
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_S_Fault1());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_S_Fault2());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_B_T_Temperature());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_V_OverallRMS());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_V_1X());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_V_6912BPFO());
			
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_V_6912BPFI());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_V_6912BSF());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_V_6912FTF());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_V_32924BPFO());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_V_32924BPFI());
			
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_V_32924BSF());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_V_32924FTF());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_V_32922BPFO());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_V_32922BPFI());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_V_32922BSF());
			
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_V_32922FTF());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_V_Crestfactor());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_V_Demodulation());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_S_Fault1());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_S_Fault2());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_B_T_Temperature());
			
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAC_h());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAC_v());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAC_a());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getFilenm());

		}
		return wb;
	}
	
	public Workbook createExcelEngdata(ExcelDownByMonitorDiagnos data, Workbook wb) {
		List<Engdata> resultList = engdataMapper.findEngdataForExcel(data);

		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		int cellNum = 19;
		Sheet sheet = wb.createSheet("엔진고장진단데이터");

		
		//header
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("엔진윤활");
		cell = row.createCell(1);
		cell.setCellValue("차량이름");
		cell = row.createCell(2);
		cell.setCellValue("시점종합");
		cell = row.createCell(3);
		cell.setCellValue("W_RPM");
		cell = row.createCell(4);
		cell.setCellValue("E_V_OverallRMS");		
		cell = row.createCell(5);
		cell.setCellValue("E_V_1-2X");
		cell = row.createCell(6);
		cell.setCellValue("E_V_1X");

		cell = row.createCell(7);
		cell.setCellValue("E_V_Crestfactor");
		cell = row.createCell(8);
		cell.setCellValue("AC_h");
		cell = row.createCell(9);
		cell.setCellValue("AC_v");
		cell = row.createCell(10);
		cell.setCellValue("AC_a");
		cell = row.createCell(11);
		cell.setCellValue("LA");
		cell = row.createCell(12);
		cell.setCellValue("LO");
		
		cell = row.createCell(13);
		cell.setCellValue("FILENM");
		
		//body
		for(Engdata s : resultList) {
			cellNum = 0;
			row = sheet.createRow(rowNum++);
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAI_ENGINE());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getSdanm());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getDate());

			
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getW_RPM());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getE_V_OverallRMS());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getE_V_1_2X());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getE_V_1X());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getE_V_Crestfactor());
			
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAC_h());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAC_v());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAC_a());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getLA());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getLO());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getFilenm());
		}
		return wb;
	}
	
	public Workbook createExcelGrbdata(ExcelDownByMonitorDiagnos data, Workbook wb) {
		List<Grbdata> resultList = grbdataMapper.findGrbdataForExcel(data);

		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		int cellNum = 17;
		Sheet sheet = wb.createSheet("기어고장진단데이터");
		
		//header
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("감속기");
		cell = row.createCell(1);
		cell.setCellValue("차량이름");
		cell = row.createCell(2);
		cell.setCellValue("시점종합");
		cell = row.createCell(3);
		cell.setCellValue("W_RPM");
		cell = row.createCell(4);
		cell.setCellValue("G_V_OverallRMS");		
		cell = row.createCell(5);
		cell.setCellValue("G_V_Wheel1X");
		cell = row.createCell(6);
		cell.setCellValue("G_V_Wheel2X");

		cell = row.createCell(7);
		cell.setCellValue("G_V_Pinion1X");
		cell = row.createCell(8);
		cell.setCellValue("G_V_Pinion2X");
		cell = row.createCell(9);
		cell.setCellValue("G_V_GMF1X");
		cell = row.createCell(10);
		cell.setCellValue("G_V_GMF2X");
		cell = row.createCell(11);
		cell.setCellValue("AC_h");
		cell = row.createCell(12);
		cell.setCellValue("AC_v");
		cell = row.createCell(13);
		cell.setCellValue("AC_a");
		cell = row.createCell(14);
		cell.setCellValue("FILENM");
		
		//body
		for(Grbdata s : resultList) {
			cellNum = 0;
			row = sheet.createRow(rowNum++);
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAI_GEAR());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getSdanm());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getDate());
			
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getW_RPM());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getG_V_OverallRMS());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getG_V_Wheel1X());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getG_V_Wheel2X());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getG_V_Pinion1X());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getG_V_Pinion2X());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getG_V_GMF1X());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getG_V_GMF2X());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAC_h());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAC_v());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAC_a());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getFilenm());
		}
		return wb;
	}
	
	public Workbook createExcelWhldata(ExcelDownByMonitorDiagnos data, Workbook wb) {
		List<Whldata> resultList = whldataMapper.findWhldataForExcel(data);

		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		int cellNum = 16;
		Sheet sheet = wb.createSheet("바퀴고장진단데이터");
		
		//header
		row = sheet.createRow(rowNum++);
		cell = row.createCell(0);
		cell.setCellValue("차륜/좌");
		cell = row.createCell(1);
		cell.setCellValue("차륜/우");
		cell = row.createCell(2);
		cell.setCellValue("SDANM");
		cell = row.createCell(3);
		cell.setCellValue("OPERDATE");
		cell = row.createCell(4);
		cell.setCellValue("OPERTIME");
		cell = row.createCell(5);
		cell.setCellValue("TIME");
		cell = row.createCell(6);
		cell.setCellValue("DATE");
		
		cell = row.createCell(7);
		cell.setCellValue("W_RPM");
		cell = row.createCell(8);
		cell.setCellValue("L_W_V_2X");
		cell = row.createCell(9);
		cell.setCellValue("L_W_V_3X");
		cell = row.createCell(10);
		cell.setCellValue("L_W_S_Fault3");
		cell = row.createCell(11);
		cell.setCellValue("R_W_V_2X");
		cell = row.createCell(12);
		cell.setCellValue("R_W_V_3X");
		cell = row.createCell(13);
		cell.setCellValue("R_W_S_Fault3");
		cell = row.createCell(14);
		cell.setCellValue("AC_h");
		cell = row.createCell(15);
		cell.setCellValue("AC_v");
		cell = row.createCell(16);
		cell.setCellValue("AC_a");
		cell = row.createCell(17);
		cell.setCellValue("FILENM");

		
		
		//body
		for(Whldata s : resultList) {
			cellNum = 0;
			row = sheet.createRow(rowNum++);
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAI_LW());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAI_RW());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getSdanm());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getOperdate());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getOpertime());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getTime());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getDate());

			
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getW_RPM());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_W_V_2X());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_W_V_3X());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getL_W_S_Fault3());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_W_V_2X());
			
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_W_V_3X());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getR_W_S_Fault3());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAC_h());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAC_v());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getAC_a());
			cell = row.createCell(cellNum++);
			cell.setCellValue(s.getFilenm());
		}
		return wb;
	}

	

//	public Map<String, Object> getAllTroubleDataForChart(troubleDataRequest data){
//		Map<String, Object> forChartData = new HashMap<String, Object>();
//		
//		switch(data.getPart().toString()) {
//			case "ber":
//				List<Berdata> listForChartBerData = berdataMapper.findForChart(data);
//				forChartData.put("data", listForChartBerData);
//				break;
//			case "eng":
//				List<Engdata> listForChartEngData = engdataMapper.findForChart(data);
//				forChartData.put("data", listForChartEngData);
//				break;
//			case "grb":
//				List<Grbdata> listForChartGrbData = grbdataMapper.findForChart(data);
//				forChartData.put("data", listForChartGrbData);
//				break;
//			default:
//				List<Whldata> listForChartWhlData = whldataMapper.findForChart(data);
//				forChartData.put("data" , listForChartWhlData);
//		}
//		return forChartData;
//	}
	
	public List<Paramdesc> getParamdescList(ParamdescRequest param) {
		List<Paramdesc> List = paramdescMapper.findParamdesc(param);
		
		return List;
	}
}








