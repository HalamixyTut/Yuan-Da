package hcux.mail.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.mail.PriorityLevelEnum;
import com.hand.hap.mail.dto.Message;
import com.hand.hap.mail.dto.MessageReceiver;

import java.util.List;
import java.util.Map;

public interface IMyMessageService extends ProxySelf<com.hand.hap.mail.service.IMessageService> {
    Message sendMessage(IRequest var1, String var2, Map<String, Object> var3, List<MessageReceiver> var4, List<Long> var5,String jobName,String errorMessage) throws Exception;

    Message sendMessage(IRequest var1, String var2, String var3, String var4, String var5, String var6, String var7, List<MessageReceiver> var8, List<Long> var9) throws Exception;


    Message addEmailMessage(Long var1, String var2, String var3, Map<String, Object> var4, List<Long> var5, List<MessageReceiver> var6) throws Exception;


    Message addEmailMessage(Long var1, String var2, String var3, String var4, PriorityLevelEnum var5, List<Long> var6, List<MessageReceiver> var7) throws Exception;


    Message sendEmailMessage(Long var1, Long var2, String var3, String var4, String var5, PriorityLevelEnum var6, List<MessageReceiver> var7, List<Long> var8) throws Exception;


    Message sendEmailMessage(Long var1, Long var2, String var3, String var4, Map<String, Object> var5, List<MessageReceiver> var6, List<Long> var7) throws Exception;


    List<Message> selectMessagesBySubject(IRequest var1, Message var2, int var3, int var4);

    List<Message> selectMessages(IRequest var1, Message var2, int var3, int var4);

    Message selectMessageContent(IRequest var1, Message var2);

    List<MessageReceiver> selectMessageAddressesByMessageId(IRequest var1, MessageReceiver var2, int var3, int var4);

    void insertData(IRequest var1, Message var2, List<MessageReceiver> var3, List<Long> var4);

    /**
     * 定时任务出错时发送邮件通知客户
     * @param iRequest
     * @param className
     * @param errorMessage
     * @throws Exception
     */
    void sendErrorMessage(IRequest iRequest,String className,String errorMessage) throws Exception;
}
