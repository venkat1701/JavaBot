package com.javadiscord.javabot.commands.configuation.config.subcommands;

import com.javadiscord.javabot.commands.SlashCommandHandler;
import com.javadiscord.javabot.other.Database;
import com.javadiscord.javabot.other.Embeds;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyAction;

public class SetSubmissionChannel implements SlashCommandHandler {
    @Override
    public ReplyAction handle(SlashCommandEvent event) {
        MessageChannel channel = event.getOption("channel").getAsMessageChannel();
        //new Database().queryConfig(event.getGuild().getId(), "channels.submission_cid", channel.getId());
        return event.replyEmbeds(Embeds.configEmbed(event, "QOTW-Submission Channel", "QOTW-Submission Channel successfully changed to", null, channel.getId(), true, true));
    }
}
