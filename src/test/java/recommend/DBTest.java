package recommend;

import org.springframework.beans.factory.annotation.Autowired;

import com.bailian.configuration.RecommendConfiguration;
import com.bailian.service.ApiConfigService;

public class DBTest {

	@Autowired
	private RecommendConfiguration rc;
	public static void main(String[] args) {
		DBTest t = new DBTest();
		t.rc.init();

	}

}
