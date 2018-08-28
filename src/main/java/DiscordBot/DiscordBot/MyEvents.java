package DiscordBot.DiscordBot;

import java.awt.print.Printable;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.audio.AudioPlayer;

public class MyEvents {
	@EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event){
		//lager musikk spilleren
		AudioPlayer audioP = AudioPlayer.getAudioPlayerForGuild(event.getGuild());
		
		if(event.getMessage().getContent().startsWith(BotUtils.BOT_PREFIX + "test")) {
        	BotUtils.sendMessage(event.getChannel(), "I am sending a message from an EventSubscriber listener");
        }  
        //Sier Hei på deg leverpostei
        else if(event.getMessage().getContent().startsWith("hei")) {
        	BotUtils.sendMessage(event.getChannel(), "Hei på deg leverpostei");
        }
        //Bot'en blir med i den voice kanalen du er i
        else if(event.getMessage().getContent().startsWith(BotUtils.BOT_PREFIX + "joinvoice")) {
        	IVoiceChannel userVoiceChannel = event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel();

            if(userVoiceChannel == null)
                return;

            userVoiceChannel.join();
        }
        //bot'en forlater den voice kanalen den er i
        else if(event.getMessage().getContent().startsWith(BotUtils.BOT_PREFIX + "leavevoice")) {
        	IVoiceChannel userVoiceChannel = event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel();

            if(userVoiceChannel == null)
                return;

         // Stop the playing track
            audioP.clear();
            
            userVoiceChannel.leave();
        }
        //Spill av en sang
        else if (event.getMessage().getContent().startsWith(BotUtils.BOT_PREFIX + "playsong")){

            
        	String args = event.getMessage().getContent();
        	args = args.replace("/playsong ", "");
        	
        	System.out.println(args);
        			
        	IVoiceChannel botVoiceChannel = event.getClient().getOurUser().getVoiceStateForGuild(event.getGuild()).getChannel();

            if(botVoiceChannel == null) {
                BotUtils.sendMessage(event.getChannel(), "Not in a voice channel, join one and then use joinvoice");
                return;
            }

            // Turn the args back into a string separated by space
            String searchStr = String.join(" ", args);
            System.out.println(searchStr);

            // Get the AudioPlayer object for the guild
            

            // Find a song given the search term
            File[] songDir = new File("music")
                    .listFiles(file -> file.getName().contains(searchStr));

            if(songDir == null || songDir.length == 0)
                return;

            // Stop the playing track
            audioP.clear();

            // Play the found song
            try {
                audioP.queue(songDir[0]);
            } catch (IOException | UnsupportedAudioFileException e) {
                BotUtils.sendMessage(event.getChannel(), "There was an issue playing that song.");
                e.printStackTrace();
            }

            BotUtils.sendMessage(event.getChannel(), "Now playing: " + songDir[0].getName());

        }
        else if(event.getMessage().getContent().startsWith(BotUtils.BOT_PREFIX + "endsong")) {
        	// Stop the playing track
            audioP.clear();
        }
    }
	
	
}
