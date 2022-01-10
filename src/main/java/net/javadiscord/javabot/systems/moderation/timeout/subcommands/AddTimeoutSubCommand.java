package net.javadiscord.javabot.systems.moderation.timeout.subcommands;


import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyAction;
import net.javadiscord.javabot.command.Responses;
import net.javadiscord.javabot.command.SlashCommandHandler;
import net.javadiscord.javabot.systems.moderation.ModerationService;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class AddTimeoutSubCommand implements SlashCommandHandler {

    @Override
    public ReplyAction handle(SlashCommandEvent event) {
        var userOption = event.getOption("user");
        var reasonOption = event.getOption("reason");
        var durationOption = event.getOption("duration");
        if (userOption == null || reasonOption == null || durationOption == null) {
            return Responses.error(event, "Missing required Arguments.");
        }
        var member = userOption.getAsMember();
        var reason = reasonOption.getAsString();
        var durations = durationOption.getAsString().split(":");
        var duration = Duration.of(Integer.parseInt(durations[0]), TimeUnit.valueOf(durations[1]).toChronoUnit());
        var channel = event.getTextChannel();
        if (channel.getType() != ChannelType.TEXT) {
            return Responses.error(event, "This command can only be performed in a server text channel.");
        }
        var quietOption = event.getOption("quiet");
        boolean quiet = quietOption != null && quietOption.getAsBoolean();

        var moderationService = new ModerationService(event.getInteraction());
        if (moderationService.timeout(member, reason, event.getMember(), duration, channel, quiet)) {
            return Responses.success(event, "User Timed Out", String.format("%s has been timed out.", member.getAsMention()));
        } else {
            return Responses.warning(event, "You're not permitted to time out this user.");
        }
    }
}