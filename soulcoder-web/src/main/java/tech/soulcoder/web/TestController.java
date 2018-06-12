package tech.soulcoder.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.soulcoder.expection.CommonException;
import tech.soulcoder.expection.CommonResponseModel;
import tech.soulcoder.service.TestService;

import java.util.Map;

@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public Map test() {

        try {
            return CommonResponseModel.success(testService.testDB());
        } catch (CommonException ue) {
            logger.error(" 异常，请检查....", ue);
            return CommonResponseModel.facade(ue.getException_type());
        } catch (Exception ex) {
            logger.error("异常，请检查....", ex);
            return CommonResponseModel.fail();
        }
    }


}
