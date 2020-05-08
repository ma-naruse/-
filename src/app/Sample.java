package app;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sample {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String ymd = format.format(date);
		System.out.println(ymd);
	}

}
