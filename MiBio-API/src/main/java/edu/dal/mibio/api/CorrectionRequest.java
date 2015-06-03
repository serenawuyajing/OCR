package edu.dal.mibio.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CorrectionRequest
{
  @XmlElement
  private String content;
  
  public String getConent() { return content; }
}