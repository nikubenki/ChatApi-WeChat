package me.xuxiaoxiao.chatapi.wechat;

import com.google.gson.Gson;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class WeChatDemo {
    /**
     * 新建一个模拟微信客户端，并绑定一个简单的监听器
     */
    public static WeChatClient wechatClient = new WeChatClient(new WeChatClient.WeChatListener() {
        @Override
        public void onQRCode(String qrCode) {
            System.out.println("onQRCode:" + qrCode);
        }

        @Override
        public void onLogin() {
            System.out.println("onLogin");
            System.out.println(String.format("您有%d名好友、活跃微信群%d个", wechatClient.userFriends().size(), wechatClient.userGroups().size()));
        }

        @Override
        public void onMessage(WXMessage message) {
            System.out.println("获取到消息:" + new Gson().toJson(message));
        }
    });

    public static void main(String[] args) {
        //启动模拟微信客户端
        wechatClient.startup();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入指令");
            switch (scanner.nextLine()) {
                case "sendText": {
                    System.out.println("toUserName:");
                    String toUserName = scanner.nextLine();
                    System.out.println("textContent:");
                    String text = scanner.nextLine();
                    wechatClient.sendText(toUserName, text);
                }
                break;
                case "sendImage": {
                    System.out.println("toUserName:");
                    String toUserName = scanner.nextLine();
                    System.out.println("imagePath:");
                    File image = new File(scanner.nextLine());
                    wechatClient.sendImage(toUserName, image);
                }
                break;
                case "revokeMsg": {
                    System.out.println("toUserName:");
                    String toUserName = scanner.nextLine();
                    System.out.println("clientMsgId:");
                    String clientMsgId = scanner.nextLine();
                    System.out.println("serverMsgId:");
                    String serverMsgId = scanner.nextLine();
                    wechatClient.revokeMsg(toUserName, clientMsgId, serverMsgId);
                }
                break;
                case "sendVerify": {
                    System.out.println("userName:");
                    String userName = scanner.nextLine();
                    System.out.println("verifyContent:");
                    String verifyContent = scanner.nextLine();
                    wechatClient.sendVerify(userName, verifyContent);
                }
                break;
                case "passVerify": {
                    System.out.println("userName:");
                    String userName = scanner.nextLine();
                    System.out.println("verifyTicket:");
                    String verifyTicket = scanner.nextLine();
                    wechatClient.passVerify(userName, verifyTicket);
                }
                break;
                case "editRemark": {
                    System.out.println("userName:");
                    String userName = scanner.nextLine();
                    System.out.println("remarkName:");
                    String remark = scanner.nextLine();
                    wechatClient.editRemark(userName, remark);
                }
                break;
                case "createGroup": {
                    System.out.println("topic:");
                    String topic = scanner.nextLine();
                    System.out.println("members,split by ',':");
                    String members = scanner.nextLine();
                    String chatroomName = wechatClient.createGroup(topic, Arrays.asList(members.split(",")));
                    System.out.println("create chatroom " + chatroomName);
                }
                break;
                case "addGroupMember": {
                    System.out.println("chatRoomName:");
                    String chatroomName = scanner.nextLine();
                    System.out.println("members,split by ',':");
                    String members = scanner.nextLine();
                    wechatClient.addGroupMember(chatroomName, Arrays.asList(members.split(",")));
                }
                break;
                case "delGroupMember": {
                    System.out.println("chatRoomName:");
                    String chatroomName = scanner.nextLine();
                    System.out.println("members,split by ',':");
                    String members = scanner.nextLine();
                    wechatClient.delGroupMember(chatroomName, Arrays.asList(members.split(",")));
                }
                break;
                case "quit":
                    System.out.println("logging out");
                    wechatClient.shutdown();
                    return;
                default:
                    System.out.println("未知指令");
                    break;
            }
        }
    }
}