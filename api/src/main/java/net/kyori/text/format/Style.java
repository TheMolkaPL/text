/*
 * This file is part of text, licensed under the MIT License.
 *
 * Copyright (c) 2017-2019 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.text.format;

import net.kyori.text.Component;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.event.HoverEvent;
import net.kyori.text.util.ToStringer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class Style implements Styled<Style, Style> {
  private static final Style EMPTY = new Style(null, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, TextDecoration.State.NOT_SET, null, null, null);;
  /**
   * The color of this component.
   */
  protected final @Nullable TextColor color;
  /**
   * If this component should have the {@link TextDecoration#OBFUSCATED obfuscated} decoration.
   */
  protected final TextDecoration.State obfuscated;
  /**
   * If this component should have the {@link TextDecoration#BOLD bold} decoration.
   */
  protected final TextDecoration.State bold;
  /**
   * If this component should have the {@link TextDecoration#STRIKETHROUGH strikethrough} decoration.
   */
  protected final TextDecoration.State strikethrough;
  /**
   * If this component should have the {@link TextDecoration#UNDERLINED underlined} decoration.
   */
  protected final TextDecoration.State underlined;
  /**
   * If this component should have the {@link TextDecoration#ITALIC italic} decoration.
   */
  protected final TextDecoration.State italic;
  /**
   * The click event to apply to this component.
   */
  protected final @Nullable ClickEvent clickEvent;
  /**
   * The hover event to apply to this component.
   */
  protected final @Nullable HoverEvent hoverEvent;
  /**
   * The string to insert when this component is shift-clicked in chat.
   */
  protected final @Nullable String insertion;

  public Style(final @Nullable TextColor color, final TextDecoration.State obfuscated, final TextDecoration.State bold, final TextDecoration.State strikethrough, final TextDecoration.State underlined, final TextDecoration.State italic, final @Nullable ClickEvent clickEvent, final @Nullable HoverEvent hoverEvent, final @Nullable String insertion) {
    this.color = color;
    this.obfuscated = obfuscated;
    this.bold = bold;
    this.strikethrough = strikethrough;
    this.underlined = underlined;
    this.italic = italic;
    this.clickEvent = clickEvent;
    this.hoverEvent = hoverEvent;
    this.insertion = insertion;
  }

  @Override
  public @Nullable TextColor color() {
    return this.color;
  }

  @Override
  public @NonNull Style color(final @Nullable TextColor color) {
    return new Style(color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
  }

  @Override
  public TextDecoration.@NonNull State decoration(final @NonNull TextDecoration decoration) {
    switch(decoration) {
      case BOLD: return this.bold;
      case ITALIC: return this.italic;
      case UNDERLINED: return this.underlined;
      case STRIKETHROUGH: return this.strikethrough;
      case OBFUSCATED: return this.obfuscated;
      default: throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
    }
  }

  @Override
  public @NonNull Style decoration(final @NonNull TextDecoration decoration, final TextDecoration.@NonNull State state) {
    switch(decoration) {
      case BOLD: return new Style(this.color, this.obfuscated, requireNonNull(state, "flag"), this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
      case ITALIC: return new Style(this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, requireNonNull(state, "flag"), this.clickEvent, this.hoverEvent, this.insertion);
      case UNDERLINED: return new Style(this.color, this.obfuscated, this.bold, this.strikethrough, requireNonNull(state, "flag"), this.italic, this.clickEvent, this.hoverEvent, this.insertion);
      case STRIKETHROUGH: return new Style(this.color, this.obfuscated, this.bold, requireNonNull(state, "flag"), this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
      case OBFUSCATED: return new Style(this.color, requireNonNull(state, "flag"), this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
      default: throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
    }
  }

  @Override
  public @Nullable ClickEvent clickEvent() {
    return this.clickEvent;
  }

  @Override
  public @NonNull Style clickEvent(final @Nullable ClickEvent event) {
    return new Style(this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, event, this.hoverEvent, this.insertion);
  }

  @Override
  public @Nullable HoverEvent hoverEvent() {
    return this.hoverEvent;
  }

  @Override
  public @NonNull Style hoverEvent(final @Nullable HoverEvent event) {
    return new Style(this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, event, this.insertion);
  }

  @Override
  public @Nullable String insertion() {
    return this.insertion;
  }

  @Override
  public @NonNull Style insertion(final @Nullable String insertion) {
    return new Style(this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, insertion);
  }

  @Override
  public @NonNull Style mergeStyle(final @NonNull Style that) {
    return new Style(that.color(), that.decoration(TextDecoration.OBFUSCATED), that.decoration(TextDecoration.BOLD), that.decoration(TextDecoration.STRIKETHROUGH), that.decoration(TextDecoration.UNDERLINED), that.decoration(TextDecoration.ITALIC), that.clickEvent(), that.hoverEvent(), that.insertion());
  }

  @Override
  public @NonNull Style mergeColor(final @NonNull Style that) {
    return new Style(that.color(), this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
  }

  @Override
  public @NonNull Style mergeDecorations(final @NonNull Style that) {
    final TextDecoration.State obfuscated = that.decoration(TextDecoration.OBFUSCATED) != TextDecoration.State.NOT_SET ? that.decoration(TextDecoration.OBFUSCATED) : this.obfuscated;
    final TextDecoration.State bold = that.decoration(TextDecoration.BOLD) != TextDecoration.State.NOT_SET ? that.decoration(TextDecoration.BOLD) : this.bold;
    final TextDecoration.State strikethrough = that.decoration(TextDecoration.STRIKETHROUGH) != TextDecoration.State.NOT_SET ? that.decoration(TextDecoration.STRIKETHROUGH) : this.strikethrough;
    final TextDecoration.State underlined = that.decoration(TextDecoration.UNDERLINED) != TextDecoration.State.NOT_SET ? that.decoration(TextDecoration.UNDERLINED) : this.underlined;
    final TextDecoration.State italic = that.decoration(TextDecoration.ITALIC) != TextDecoration.State.NOT_SET ? that.decoration(TextDecoration.ITALIC) : this.italic;
    return new Style(this.color, obfuscated, bold, strikethrough, underlined, italic, this.clickEvent, this.hoverEvent, this.insertion);
  }

  @Override
  public @NonNull Style mergeEvents(final @NonNull Style that) {
    return new Style(this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, that.clickEvent(), that.hoverEvent(), this.insertion);
  }

  @Override
  public @NonNull Style resetStyle() {
    return EMPTY;
  }

  @Override
  public boolean hasStyling() {
    // A component has styling when any of these fields are set.
    return this.color != null
      || this.obfuscated != TextDecoration.State.NOT_SET
      || this.bold != TextDecoration.State.NOT_SET
      || this.strikethrough != TextDecoration.State.NOT_SET
      || this.underlined != TextDecoration.State.NOT_SET
      || this.italic != TextDecoration.State.NOT_SET
      || this.clickEvent != null
      || this.hoverEvent != null
      || this.insertion != null;
  }

  @Override
  public @NonNull String toString() {
    final Map<String, Object> builder = new LinkedHashMap<>();
    builder.put("color", this.color);
    builder.put("obfuscated", this.obfuscated);
    builder.put("bold", this.bold);
    builder.put("strikethrough", this.strikethrough);
    builder.put("underlined", this.underlined);
    builder.put("italic", this.italic);
    builder.put("clickEvent", this.clickEvent);
    builder.put("hoverEvent", this.hoverEvent);
    builder.put("insertion", this.insertion);
    return ToStringer.toString(this, builder);
  }

  @Override
  public boolean equals(final @Nullable Object other) {
    if(this == other) return true;
    if(other == null || !(other instanceof Style)) return false;
    final Style that = (Style) other;
    return this.color == that.color
      && Objects.equals(this.obfuscated, that.obfuscated)
      && Objects.equals(this.bold, that.bold)
      && Objects.equals(this.strikethrough, that.strikethrough)
      && Objects.equals(this.underlined, that.underlined)
      && Objects.equals(this.italic, that.italic)
      && Objects.equals(this.clickEvent, that.clickEvent)
      && Objects.equals(this.hoverEvent, that.hoverEvent)
      && Objects.equals(this.insertion, that.insertion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
  }

  /**
   * A style builder.
   */
  protected static class Builder {
    /**
     * The color of this component.
     */
    protected @Nullable TextColor color;
    /**
     * If this component should have the {@link TextDecoration#OBFUSCATED obfuscated} decoration.
     */
    protected TextDecoration.State obfuscated = TextDecoration.State.NOT_SET;
    /**
     * If this component should have the {@link TextDecoration#BOLD bold} decoration.
     */
    protected TextDecoration.State bold = TextDecoration.State.NOT_SET;
    /**
     * If this component should have the {@link TextDecoration#STRIKETHROUGH strikethrough} decoration.
     */
    protected TextDecoration.State strikethrough = TextDecoration.State.NOT_SET;
    /**
     * If this component should have the {@link TextDecoration#UNDERLINED underlined} decoration.
     */
    protected TextDecoration.State underlined = TextDecoration.State.NOT_SET;
    /**
     * If this component should have the {@link TextDecoration#ITALIC italic} decoration.
     */
    protected TextDecoration.State italic = TextDecoration.State.NOT_SET;
    /**
     * The click event to apply to this component.
     */
    protected @Nullable ClickEvent clickEvent;
    /**
     * The hover event to apply to this component.
     */
    protected @Nullable HoverEvent hoverEvent;
    /**
     * The string to insert when this component is shift-clicked in chat.
     */
    protected @Nullable String insertion;

    protected Builder() {
    }

    protected Builder(final @NonNull Component component) {
      this.color = component.color();
      this.obfuscated = component.decoration(TextDecoration.OBFUSCATED);
      this.bold = component.decoration(TextDecoration.BOLD);
      this.strikethrough = component.decoration(TextDecoration.STRIKETHROUGH);
      this.underlined = component.decoration(TextDecoration.UNDERLINED);
      this.italic = component.decoration(TextDecoration.ITALIC);
      this.clickEvent = Optional.ofNullable(component.clickEvent()).map(ClickEvent::copy).orElse(null);
      this.hoverEvent = Optional.ofNullable(component.hoverEvent()).map(HoverEvent::copy).orElse(null);
      this.insertion = component.insertion();
    }

    /**
     * Sets the color.
     *
     * @param color the color
     * @return this builder
     */
    public @NonNull Builder color(final @Nullable TextColor color) {
      this.color = color;
      return this;
    }

    /**
     * Sets the color if there isn't one set already.
     *
     * @param color the color
     * @return this builder
     */
    public @NonNull Builder colorIfAbsent(final @Nullable TextColor color) {
      if(this.color == null) {
        this.color = color;
      }
      return this;
    }

    /**
     * Sets the value of a decoration.
     *
     * @param decoration the decoration
     * @param state {@link TextDecoration.State#TRUE} if this component should have the
     *     decoration, {@link TextDecoration.State#FALSE} if this component should not
     *     have the decoration, and {@link TextDecoration.State#NOT_SET} if the decoration
     *     should not have a set value
     * @return this builder
     */
    public @NonNull Builder decoration(final @NonNull TextDecoration decoration, final TextDecoration.@NonNull State state) {
      switch(decoration) {
        case BOLD: this.bold = requireNonNull(state, "flag"); return this;
        case ITALIC: this.italic = requireNonNull(state, "flag"); return this;
        case UNDERLINED: this.underlined = requireNonNull(state, "flag"); return this;
        case STRIKETHROUGH: this.strikethrough = requireNonNull(state, "flag"); return this;
        case OBFUSCATED: this.obfuscated = requireNonNull(state, "flag"); return this;
        default: throw new IllegalArgumentException(String.format("unknown decoration '%s'", decoration));
      }
    }

    /**
     * Sets the click event.
     *
     * @param event the click event
     * @return this builder
     */
    public @NonNull Builder clickEvent(final @Nullable ClickEvent event) {
      this.clickEvent = event;
      return this;
    }

    /**
     * Sets the hover event.
     *
     * @param event the hover event
     * @return this builder
     */
    public @NonNull Builder hoverEvent(final @Nullable HoverEvent event) {
      this.hoverEvent = event;
      return this;
    }

    /**
     * Sets the string to be inserted.
     *
     * @param insertion the insertion string
     * @return this builder
     */
    public @NonNull Builder insertion(final @Nullable String insertion) {
      this.insertion = insertion;
      return this;
    }

    public @NonNull Style build() {
      return new Style(this.color, this.obfuscated, this.bold, this.strikethrough, this.underlined, this.italic, this.clickEvent, this.hoverEvent, this.insertion);
    }
  }
}