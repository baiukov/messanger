import me.chatserver.database.MessageRepository;
import me.chatserver.database.UserRepository;
import me.chatserver.entities.Message;
import me.chatserver.entities.User;
import me.chatserver.services.AppService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class test {


    @Test
    public void test() {

        AppService appService = AppService.getAppService();
//        appService.sendMessage(new String[]
//                {"SEND", "92dbc57e-21be-4557-b14f-14233ac73e52", "92dbc57e-21be-4557-b14f-14233ac73e52", "asd"});
        MessageRepository messageRepository = new MessageRepository();
        List<Object[]> res = (List<Object[]>) messageRepository.getMessagesByUserID("92dbc57e-21be-4557-b14f-14233ac73e52");
        System.out.println(res);
        for (Object[] row : res) {
            System.out.println(row[0] + " " + row[1] + " " + row[2] + row[3]);
        }
    }

    @Test
    public void testSave() {
        AppService appService = AppService.getAppService();
        appService.sendMessage(new String[]
                {"SEND", "92dbc57e-21be-4557-b14f-14233ac73e52", "92dbc57e-21be-4557-b14f-14233ac73e52", "asd"});
    }
}
