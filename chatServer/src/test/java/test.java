import me.chatserver.database.UserRepository;
import me.chatserver.services.AppService;
import org.junit.jupiter.api.Test;

public class test {


    @Test
    public void test() {

        AppService appService = AppService.getAppService();
        appService.sendMessage(new String[]
                {"SEND", "92dbc57e-21be-4557-b14f-14233ac73e52", "92dbc57e-21be-4557-b14f-14233ac73e52", "asd"});
    }
}
