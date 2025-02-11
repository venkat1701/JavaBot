package net.javadiscord.javabot.systems.configuration.subcommands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyAction;
import net.javadiscord.javabot.Bot;
import net.javadiscord.javabot.command.Responses;
import net.javadiscord.javabot.command.SlashCommandHandler;
import net.javadiscord.javabot.data.config.UnknownPropertyException;

public class SetSubcommand implements SlashCommandHandler {
	@Override
	public ReplyAction handle(SlashCommandEvent event) {
		var propertyOption = event.getOption("property");
		var valueOption = event.getOption("value");
		if (propertyOption == null || valueOption == null) {
			return Responses.warning(event, "Missing required arguments.");
		}
		String property = propertyOption.getAsString().trim();
		String valueString = valueOption.getAsString().trim();
		try {
			Bot.config.get(event.getGuild()).set(property, valueString);
			return Responses.success(event, "Configuration Updated", String.format("The property `%s` has been set to `%s`.", property, valueString));
		} catch (UnknownPropertyException e) {
			return Responses.warning(event, "Unknown Property", "The property `" + property + "` could not be found.");
		}
	}
}
