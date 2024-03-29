/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.gonevertical.core.client.input.richtext.workaround;

import com.google.gwt.event.logical.shared.HasInitializeHandlers;
import com.google.gwt.event.logical.shared.InitializeEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;


/**
 * Base class for RichText platform implementations. The default version simply
 * creates a text area with no rich text support.
 * 
 * This is not currently used by any user-agent, but will provide a
 * &lt;textarea&gt; fallback in the event a future browser fails to implement
 * rich text editing.
 */
public class RichTextAreaImpl {

  protected Element elem;
  
  protected HasInitializeHandlers owner;

  public RichTextAreaImpl() {
    elem = createElement();
  }

  public Element getElement() {
    return elem;
  }

  public String getHTML() {
    return DOM.getElementProperty(elem, "value");
  }

  public String getText() {
    return DOM.getElementProperty(elem, "value");
  }

  public void initElement() {
    onElementInitialized();
  }

  public boolean isEnabled() {
    return !elem.getPropertyBoolean("disabled");
  }

  public void setEnabled(boolean enabled) {
    elem.setPropertyBoolean("disabled", !enabled);
  }

  public native void setFocus(boolean focused) /*-{
    if (focused) {
      this.@org.gonevertical.core.client.input.richtext.workaround.RichTextAreaImpl::elem.focus();
    } else {
      this.@org.gonevertical.core.client.input.richtext.workaround.RichTextAreaImpl::elem.blur();
    }
  }-*/;

  public void setHTML(String html) {
    DOM.setElementProperty(elem, "value", html);
  }

  public void setOwner(HasInitializeHandlers owner) {
    this.owner = owner;
  }

  public void setText(String text) {
    DOM.setElementProperty(elem, "value", text);
  }
  
  public void setVisible(boolean visible) {
    setVisible(elem, visible);
  }
  
  private native void setVisible(Element elem, boolean visible) /*-{
    elem.style.display = visible ? '' : 'none';
  }-*/;

  /**
   * @deprecated as of GWT 2.1, use {@link #setOwner(HasInitializeHandlers)}
   *             instead
   */
  @Deprecated
  public void setWidget(RichTextArea richTextWidget) {
    setOwner(richTextWidget);
  }

  public void uninitElement() {
  }

  protected Element createElement() {
    return DOM.createTextArea();
  }

  protected void hookEvents() {
    DOM.sinkEvents(elem, Event.MOUSEEVENTS | Event.KEYEVENTS | Event.ONCHANGE
        | Event.ONCLICK | Event.FOCUSEVENTS | Event.ONPASTE | Event.ONDBLCLICK);
  }

  protected void onElementInitialized() {
    hookEvents();
    if (owner != null) {
      InitializeEvent.fire(owner);
    }
  }
  
}
