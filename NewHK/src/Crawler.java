import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.dao2.Agency;
import com.dao2.AgencyDAO;
import com.dao2.NewDAO;

public class Crawler {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		String urlFirst = "http://ipsearch.ipd.gov.hk/trademark/jsp/fssr01001s_schi.jsp?SOAPQC=-1&FILE_NO_TYPE=A&FILE_NO={0}&FILE_NO_SMM=5&FILE_NO={1}&FILE_NO_SMM=3&TM_TEXT=&TM_TEXT_SMM=0&TM_TEXT_OP=0&TM_TEXT=&TM_TEXT_SMM=0&TM_TEXT_OP=0&TM_TEXT=&TM_TEXT_SMM=0&TM_CODE=&TM_CODE_OP=1&CLASS_NO=&MARK_TYPE=Y&SPECIAL_SIGNS=Y&COLOUR_CLAIM_DESCRIP_STYLE=&COLOUR_CLAIM_DESCRIP_STYLE_SMM=0&APPL_NAME=&APPL_NAME_SMM=0&LICENCEE=&LICENCEE_SMM=0&ADD_FOR_SERVICE=&ADD_FOR_SERVICE_SMM=0&FILE_DT=&FILE_DT_SMM=5&FILE_DT=&FILE_DT_SMM=3&RREG_DT=&RREG_DT_SMM=5&RREG_DT=&RREG_DT_SMM=3&EXP_DT=&EXP_DT_SMM=5&EXP_DT=&EXP_DT_SMM=3&PUB_DT=&PUB_DT_SMM=5&PUB_DT=&PUB_DT_SMM=3&TM_SPEC=&TM_SPEC_SMM=0&TM_DIS=&TM_DIS_SMM=6&STYPE=S&TYPE=S&LIVE_STATUS=Y";
		String urlSecend = "";
		Agency agency = new Agency();
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 15, 300,
			       TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10),
			    new ThreadPoolExecutor.CallerRunsPolicy());
		new AgencyDAO().run();
		for(int i = 19920010; i <= 19921000; i += 20){
			Thread.sleep(10000);
			if(Agency.getProxs().size() <= 3){
				System.out.println("¿ªÊ¼");
				new AgencyDAO().run();
				Thread.sleep(3000);
			}
			urlSecend = MessageFormat.format(urlFirst, String.valueOf(i), String.valueOf(i + 19));
			executor.execute(new NewDAO(urlSecend));
		}
		executor.shutdown();
	}

}
