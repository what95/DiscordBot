package DiscordBot.DiscordBot;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IVoiceChannel;

public class MyEvents {
	@EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getMessage().getContent().startsWith(BotUtils.BOT_PREFIX + "test"))
            BotUtils.sendMessage(event.getChannel(), "I am sending a message from an EventSubscriber listener");
        
        else if(event.getMessage().getContent().startsWith("hei")) {
        	BotUtils.sendMessage(event.getChannel(), "Hei på deg leverpostei");
        }
        else if(event.getMessage().getContent().startsWith(BotUtils.BOT_PREFIX + "joinvoice")) {
        	IVoiceChannel userVoiceChannel = event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel();

            if(userVoiceChannel == null)
                return;

            userVoiceChannel.join();
        }
        else if(event.getMessage().getContent().startsWith(BotUtils.BOT_PREFIX + "leavevoice")) {
        	IVoiceChannel userVoiceChannel = event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel();

            if(userVoiceChannel == null)
                return;

            userVoiceChannel.leave();
        }
    }
	
	
}
