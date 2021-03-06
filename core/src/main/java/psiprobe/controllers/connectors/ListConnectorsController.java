/**
 * Licensed under the GPL License. You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE.
 */
package psiprobe.controllers.connectors;

import org.springframework.web.servlet.ModelAndView;

import psiprobe.beans.ContainerListenerBean;
import psiprobe.controllers.TomcatContainerController;
import psiprobe.model.Connector;
import psiprobe.model.RequestProcessor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Class ListConnectorsController.
 */
public class ListConnectorsController extends TomcatContainerController {

  /** The container listener bean. */
  private ContainerListenerBean containerListenerBean;

  /** The include request processors. */
  private boolean includeRequestProcessors;

  /** The collection period. */
  private long collectionPeriod;

  /**
   * Gets the container listener bean.
   *
   * @return the container listener bean
   */
  public ContainerListenerBean getContainerListenerBean() {
    return containerListenerBean;
  }

  /**
   * Sets the container listener bean.
   *
   * @param containerListenerBean the new container listener bean
   */
  public void setContainerListenerBean(ContainerListenerBean containerListenerBean) {
    this.containerListenerBean = containerListenerBean;
  }

  /**
   * Gets the collection period.
   *
   * @return the collection period
   */
  public long getCollectionPeriod() {
    return collectionPeriod;
  }

  /**
   * Sets the collection period.
   *
   * @param collectionPeriod the new collection period
   */
  public void setCollectionPeriod(long collectionPeriod) {
    this.collectionPeriod = collectionPeriod;
  }

  /**
   * Checks if is include request processors.
   *
   * @return true, if is include request processors
   */
  public boolean isIncludeRequestProcessors() {
    return includeRequestProcessors;
  }

  /**
   * Sets the include request processors.
   *
   * @param includeRequestProcessors the new include request processors
   */
  public void setIncludeRequestProcessors(boolean includeRequestProcessors) {
    this.includeRequestProcessors = includeRequestProcessors;
  }

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    boolean workerThreadNameSupported = false;
    List<Connector> connectors = containerListenerBean.getConnectors(includeRequestProcessors);

    if (!connectors.isEmpty()) {
      List<RequestProcessor> reqProcs = connectors.get(0).getRequestProcessors();
      if (!reqProcs.isEmpty()) {
        RequestProcessor reqProc = reqProcs.get(0);
        workerThreadNameSupported = reqProc.isWorkerThreadNameSupported();
      }
    }

    return new ModelAndView(getViewName()).addObject("connectors", connectors)
        .addObject("workerThreadNameSupported", workerThreadNameSupported)
        .addObject("collectionPeriod", getCollectionPeriod());
  }

}
