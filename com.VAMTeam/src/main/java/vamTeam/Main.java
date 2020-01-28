package vamTeam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import com.rgenerator.excel.ExcelSaveData;

public class Main {

	public static void main(String[] args) {

		ExcelSaveData ex = new ExcelSaveData();
		ex.startMonthlyReporting(LocalDate.now().minusDays(1).withDayOfMonth(1), LocalDate.now().minusDays(1));
		ex.startWeeklyReporting(LocalDate.now().minusDays(1).with(DayOfWeek.MONDAY), LocalDate.now().minusDays(1));

		int month = LocalDate.now().getMonthValue();

		if (month >= 1 && month <= 3) // Q1
			ex.startQuarterReporting(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 3, 31));
		else if (month >= 4 && month <= 6) // Q2
			ex.startQuarterReporting(LocalDate.of(2020, 4, 1), LocalDate.of(2020, 6, 30));
		else if (month >= 7 && month <= 9) // Q2
			ex.startQuarterReporting(LocalDate.of(2020, 7, 1), LocalDate.of(2020, 9, 31));
		else if (month >= 10 && month <= 12) // Q2
			ex.startQuarterReporting(LocalDate.of(2020, 10, 1), LocalDate.of(2020, 12, 31));

	}

}
