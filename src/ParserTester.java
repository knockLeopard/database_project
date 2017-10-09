import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

public class ParserTester {
	public static SimpleDBMSParser p = null;
	
	public final ArrayList<String> createTableQuery = new ArrayList<>(
			Arrays.asList("create table account (\n" + 
					"account_number int not null, \n" + 
					"branch_name char(15), \n" + 
					"primary key(account_number)\n" + 
					"); \n")
			);
	public final ArrayList<String> dropTableQuery = new ArrayList<>(
			Arrays.asList("drop table a;")
			);
	public final ArrayList<String> descQuery = new ArrayList<>(
			Arrays.asList("desc a;")
			);
	public final ArrayList<String> showTablesQuery = new ArrayList<>(
			Arrays.asList("show tables;")
			);
	public final ArrayList<String> selectQuery = new ArrayList<>(
			Arrays.asList("select customer_name, borrower.loan_number, amount \n" + 
					"from borrower, loan \n" + 
					"where borrower.loan_number = loan.loan_number and branch_name = 'Perryridge'; \n")
			);
	public final ArrayList<String> insertQuery = new ArrayList<>(
			Arrays.asList("insert into account values(9732, 'Perryridge'); \n")
			);
	public final ArrayList<String> deleteQuery = new ArrayList<>(
			Arrays.asList("delete from accounts; \n", 
					"delete from accounts where a >= b; \n")
			);
	
	public void test(int queryType, ArrayList<String> queries) {
		queries.forEach(q -> {
			try {
				if (p == null) {
					p = new SimpleDBMSParser(new ByteArrayInputStream(q.getBytes()));
				} else {
					p.ReInit(new ByteArrayInputStream(q.getBytes("UTF-8")));
				}
				assertEquals(p.query(), queryType);
			} catch(ParseException | UnsupportedEncodingException e) {
				System.out.printf(q);
			}
		});
	}
	
	@Test
	public void testSingleQuery() throws ParseException {
		test(SimpleDBMSParser.PRINT_CREATE_TABLE, createTableQuery);
		test(SimpleDBMSParser.PRINT_DROP_TABLE, dropTableQuery);
		test(SimpleDBMSParser.PRINT_DESC, descQuery);
		test(SimpleDBMSParser.PRINT_SHOW_TABLES, showTablesQuery);
		test(SimpleDBMSParser.PRINT_SELECT, selectQuery);
		test(SimpleDBMSParser.PRINT_INSERT, insertQuery);
		test(SimpleDBMSParser.PRINT_DELETE, deleteQuery);
	}
}