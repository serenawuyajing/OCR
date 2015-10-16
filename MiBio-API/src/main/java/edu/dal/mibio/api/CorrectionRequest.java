package edu.dal.mibio.api;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CorrectionRequest
{
  @XmlElement
  private String content;

  @XmlElement
  private List<Boolean> options;
  
  public String getConent() { return content; }

  public List<Boolean> getOptions() { return options; }

}
