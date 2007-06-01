/**********************************************************************************
* $URL$
* $Id$
***********************************************************************************
*
* Copyright (c) 2003, 2004 The Sakai Foundation.
*
* Licensed under the Educational Community License, Version 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.opensource.org/licenses/ecl1.php
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
**********************************************************************************/

package org.sakaiproject.jsf.renderer;

import java.io.IOException;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sakaiproject.jsf.util.JSFDepends;
import org.sakaiproject.jsf.util.RendererUtil;


public class ToolBarItemRenderer extends JSFDepends.CommandLinkRenderer
{
  public boolean supportsComponentType(UIComponent component)
  {
    return (component instanceof org.sakaiproject.jsf.component.ToolBarItemComponent);
  }

  public void encodeBegin(FacesContext context, UIComponent component) throws IOException
  {
      if (!component.isRendered()) return;

    if (!isDisabled(context, component))
    {
        // use default link rendering
      super.encodeBegin(context, component);
    }
    else
    {
        // setup to render the disabled link ourselves
      ResponseWriter writer = context.getResponseWriter();
      writer.write("<span class=\"inactive\">");
    }
  }

  public void encodeChildren(FacesContext context, UIComponent component) throws IOException
  {
      if (!component.isRendered()) return;

      if (!isDisabled(context, component))
      {
          // use default rendering
          super.encodeChildren(context, component);
      }
      else
      {
          // render the text of the disabled link ourselves
      String label = "";
      Object value = ((UICommand) component).getValue();
      if (value != null)
      {
      label = value.toString();
      }

      ResponseWriter writer = context.getResponseWriter();
      writer.write(label);
      }
  }

  public void encodeEnd(FacesContext context, UIComponent component) throws IOException
  {
      if (!component.isRendered()) return;

    if (!isDisabled(context, component))
    {
        // use default link rendering
      super.encodeEnd(context, component);
    }
    else
    {
        // finish rendering the disabled link ourselves
      ResponseWriter writer = context.getResponseWriter();
      writer.write("</span>");
    }
  }

  /**
   * Check if the component is disabled.
   * @param component
   * @return true if the component has a boolean "disabled" attribute set, false if not
   */
  protected boolean isDisabled(FacesContext context, UIComponent component)
  {
    boolean disabled = false;
    Object value = RendererUtil.getAttribute(context, component, "disabled");
    if (value != null)
    {
      if (value instanceof Boolean)
      {
        disabled = ((Boolean) value).booleanValue();
      }
      else
      {
        if (!(value instanceof String))
        {
          value = value.toString();
        }
        disabled = (new Boolean((String) value)).booleanValue();
      }
    }

    return disabled;
  }
}



