package com.projetdiver.session;

import com.projetdiver.diver.Diver;
import com.projetdiver.session.invitation.Invitation;

import java.util.ArrayList;

public abstract class SessionDAO {

    /** instance de SessionDAO Postgre */
    private static SessionDAO instance;

    /**
     * Default constructor
     */
    public SessionDAO() {}

    /**
     * get the instance of the SessionDAO
     * Not thread safe
     */
    public  static SessionDAO getInstance() {
        if (SessionDAO.instance == null) {
            return new SessionDAOPostgre();
        } else {
            return SessionDAO.instance;
        }
    }

    abstract Session getSession(Integer sessionId);

    abstract Session getSession(String title);

    abstract boolean createSession(Session session);

    abstract boolean modifySession(Session session);

    abstract boolean deleteSession(Session session);

    abstract Invitation getInvitation(Session session, Diver receiver);

    abstract ArrayList<Invitation> getAllInvitationsToDiver(Diver receiver);

    abstract ArrayList<Invitation> getAllInvitationsToSession(Session session);

    abstract boolean createInvitation(Invitation invitation);

    abstract boolean deleteInvitation(Invitation invitation);

}
