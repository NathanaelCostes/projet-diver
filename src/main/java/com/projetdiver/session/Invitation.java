package com.projetdiver.session;

import com.projetdiver.diver.Diver;

/**
 * @Author Boudier
 */
public class Invitation {
    private Session session;

    private Diver receiver;

    private boolean pending;

    public Invitation(Session session, Diver receiver, boolean pending) {
        this.session = session;
        this.receiver = receiver;
        this.pending = pending;
    }

    public Invitation() {}

    public Session getSession() {
        return session;
    }

    public Diver getReceiver() {
        return receiver;
    }

    public boolean isPending() {
        return pending;
    }

    public void acceptInvitation() {
        // Add warning if invitation is already accepted
        this.pending = true;
    }
}
