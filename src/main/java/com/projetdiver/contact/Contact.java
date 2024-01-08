package com.projetdiver.contact;

import com.projetdiver.diver.Diver;

/**
 * This class is the contact between two divers
 */
public class Contact {

    /** receiver of the contact */
    private Diver receiver;

    /** sender of the contact */
    private Diver sender;

    /** pending of contact request */
    private boolean pending;

    /**
     * Create a contact
     * @param receiver of the contact
     * @param sender of the contact
     * @param pending of the contact
     */
    public Contact(Diver receiver, Diver sender, boolean pending) {
        this.receiver = receiver;
        this.sender = sender;
        this.pending = pending;
    }

    public Contact() {}

    /**
     * @return receiver
     */
    public Diver getReceiver() {
        return receiver;
    }

    /**
     * @param receiver to set
     */
    public void setReceiver(Diver receiver) {
        this.receiver = receiver;
    }

    /**
     * @return sender
     */
    public Diver getSender() {
        return sender;
    }

    /**
     * @param sender to set
     */
    public void setSender(Diver sender) {
        this.sender = sender;
    }

    /**
     * @return pending
     */
    public boolean isPending() {
        return pending;
    }

    /**
     * @param pending to set
     */
    public void setPending(boolean pending) {
        this.pending = pending;
    }

    @Override
    public String toString() {
        return "Contact [pending=" + pending + ", receiver=" + receiver + ", sender=" + sender + "]";
    }

    /*
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Contact)) {
            return false;
        }
        Contact other = (Contact) obj;
        return this.receiver.equals(other.receiver) && this.sender.equals(other.sender);
    }
    */
}
