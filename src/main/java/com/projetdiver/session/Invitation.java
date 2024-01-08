package com.projetdiver.session;

import com.projetdiver.diver.Diver;

/**
 * This class is the invitation of a diver to a session
 * @author  Boudier
 */
public class Invitation {

    /**
     * The session of the invitation
     */
    private Session session;

    /**
     * The receiver of the invitation
     */
    private Diver receiver;

    /**
     * The status of the invitation
     */
    private boolean pending;

    /**
     * Default constructor
     * @param session the session of the invitation
     * @param receiver the receiver of the invitation
     * @param pending the status of the invitation
     */
    public Invitation(Session session, Diver receiver, boolean pending) {
        this.session = session;
        this.receiver = receiver;
        this.pending = pending;
    }

    /**
     * Default constructor
     */
    public Invitation() {}

    /**
     * @return the session of the invitation
     */
    public Session getSession() {
        return session;
    }

    /**
     * @return the receiver of the invitation
     */
    public Diver getReceiver() {
        return receiver;
    }

    /**
     * @return the status of the invitation
     */
    public boolean isPending() {
        return pending;
    }

    /**
     * Accept the invitation
     */
    public void acceptInvitation() {
        // Add warning if invitation is already accepted
        this.pending = true;
    }
}
