package wtf.violet.bot.command;

import net.dv8tion.jda.api.entities.Member;

public class ArgumentWrapper {

  private String text;
  private Member member;

  public ArgumentWrapper(String text) {
    this.text = text;
  }

  public ArgumentWrapper(Member member) {
    this.member = member;
  }

  public String getText() {
    return text;
  }

  public Member getMember() {
    return member;
  }
}
