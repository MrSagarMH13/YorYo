package com.yoryo.assignment.controller;

import org.atmosphere.cpr.MetaBroadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yoryo.assignment.atmosphere.BroadcastService;


/**
 * @author Sagar
 *
 */
@RestController
public class TrackingController {

  private final MetaBroadcaster broadcaster;

  /**
   * Creates a new instance of {@link TrackingController}.
   * 
   * @param metaBroadcaster the atmosphere meta broadcaster.
   */
  @Autowired
  public TrackingController(MetaBroadcaster metaBroadcaster) {
    if (metaBroadcaster == null) {
      throw new NullPointerException("metaBroadcaster must not be null");
    }
    this.broadcaster = metaBroadcaster;
  }

  /**
   * Home action.
   * 
   * @return the index page.
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String home() {
    return "index";
  }

  /**
   * Broadcast a message to all registered clients.
   * 
   * @param message to broadcast.
   */
  @RequestMapping(value = "/broadcast/{message}", method = RequestMethod.GET)
  @ResponseStatus(value = HttpStatus.OK)
  public void broadcast(@PathVariable("message") String message) {
    broadcaster.broadcastTo(BroadcastService.PATH, message);
  }

}
