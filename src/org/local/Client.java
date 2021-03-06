package org.local;

import java.net.UnknownHostException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.interfaces.MessageBusI;
import org.interfaces.ClientI;
import org.interfaces.MessageListenerI;
import org.utils.MessageObject;

/**
 * The Class Client.
 */
public class Client implements ClientI {
	
	/** The id. */
	private String id;
	
	/** The message listener. */
	private MessageListenerI messageListener;
	
	/** The message bus. */
	private MessageBusI messageBus = null;

	
	/**
	 * Instantiates a new Client.
	 */
	public Client(){
		this.id = UUID.randomUUID().toString();
	}
	
	public Client(String id ){
		this.id = id;
	}

	public Client(String id , String ipAddress, int port){
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see org.interfaces.ClientI#connect(org.interfaces.MessageBusI)
	 */
	@Override
	public void connect(MessageBusI messageBus) {
		this.messageBus = messageBus;
		messageBus.registerSubscriber(this);
	}

	/* (non-Javadoc)
	 * @see org.interfaces.ClientI#disconnect(org.interfaces.MessageBusI)
	 */
	@Override
	public void disconnect(MessageBusI messageBus) {
		this.messageBus = null;
	}

	/* (non-Javadoc)
	 * @see org.interfaces.ClientI#subscribe(java.lang.String)
	 */
	@Override
	public void subscribe(String topic){
		if(this.messageBus != null)
			this.messageBus.addTopicToSubscriber(topic, this);	
		
	}

	/* (non-Javadoc)
	 * @see org.interfaces.ClientI#unsubscribe(java.lang.String)
	 */
	@Override
	public void unsubscribe(String topic){
		if(this.messageBus != null)
			this.messageBus.removeTopicFromSubscriber(topic, this);
	}

	/* (non-Javadoc)
	 * @see org.interfaces.ClientI#addMessageListener(org.listeners.MessageListenerI)
	 */
	@Override
	public void addMessageListener(MessageListenerI messageListener){
		this.messageListener = messageListener;
	}
	
	/* (non-Javadoc)
	 * @see org.interfaces.ClientI#callMessageListener(java.lang.String)
	 */
	@Override
	public void callMessageListener(MessageObject message){
		this.messageListener.handleReceivedMessage(message);	
	}
	
	/* (non-Javadoc)
	 * @see org.interfaces.PublisherI#publish(java.lang.String, java.lang.String)
	 */
	@Override
	public void publish(String topic, String message) {
		
		this.messageBus.dispatch(topic, message);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Client[" + id + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
