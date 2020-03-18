package be.pxl.student.util;

import com.github.javafaker.Faker;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Random fake data generator.
 * This class will generate some random data
 */
public class BudgetPlannerFeeder {
	private static final Random RANDOM = new Random();

	private Faker faker = new Faker();

	private String myAccountName = "Jos";
	private String myIBANNumber = "BE69771770897312";

	public static void main(String[] args) throws IOException {
		BudgetPlannerFeeder feeder = new BudgetPlannerFeeder();
		List<String> dataLines = feeder.generateLines(100);
		feeder.printLines(dataLines);
		feeder.saveFile("src/main/resources/account_payments.csv", dataLines);
	}

	private void saveFile(String csvFile, List<String> dataLines) throws IOException {
		try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvFile), StandardOpenOption.WRITE)) {
			for (String dataLine : dataLines) {
				writer.write(dataLine);
				writer.newLine();
			}
		}
	}

	private void printLines(List<String> dataLines) {
		for (String line: dataLines) {
			System.out.println(line);
		}
	}

	private List<String> generateLines(int total) {
		List<String> data = new ArrayList<>();
		addHeaderLine(data);
		for (int i = 0; i < total; i++) {
			int numbOfPayments = RANDOM.nextInt(10) + 1;

			String accountName = faker.name().firstName();
			String ibanNumber = faker.finance().iban("BE");
			for (int j = 0; j < numbOfPayments; j++) {
				StringBuilder buffer = new StringBuilder();
				buffer.append(accountName).append(","); // Account name
				buffer.append(ibanNumber).append(","); // Account IBAN
				buffer.append(faker.finance().iban("BE")).append(","); // Account IBAN
				buffer.append(faker.date()
						.past(RANDOM.nextInt(30) + 1, TimeUnit.DAYS))
						.append(","); // Transaction date between now and 30 days ago
				buffer.append(faker.number().randomDouble(2, -5000, 5000)).append(","); // Amount
				buffer.append("EUR").append(","); // Currency
				buffer.append(faker.lorem().sentence());
				data.add(buffer.toString());
			}
		}
		return data;
	}

	private void addHeaderLine(List<String> data) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Account name").append(",");
		buffer.append("Account IBAN").append(",");
		buffer.append("Counteraccount IBAN").append(",");
		buffer.append("Transaction date").append(",");
		buffer.append("Amount").append(",");
		buffer.append("Currency").append(",");
		buffer.append("Detail");
		data.add(buffer.toString());
	}


	public void feedRandomData() {

		// ...

	}
}
