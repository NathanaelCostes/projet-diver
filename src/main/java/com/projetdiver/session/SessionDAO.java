package com.projetdiver.session;

import com.projetdiver.diver.Diver;

import java.util.ArrayList;

/**
 * Represents the abstract class of the SessionDAO
 * @author Boudier
 */
public abstract class  SessionDAO {

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

    /**
     * @param sessionId the id of the session
     * @return the session with the id
     */
    abstract Session getSession(Integer sessionId);

    /**
     * @param owner the owner of the session
     * @param title the title of the session
     * @return the session with the owner and the title
     */
    abstract Session getSession(Diver owner, String title);

    /**
     * @param owner the owner of the session
     * @return the session with the owner
     */
    public abstract ArrayList<Session> getAllSessionsWhereDiverIsOwner(Diver owner);

    /**
     * @param diver the diver of the session
     * @param pending the pending of the session
     * @return the session with the diver and the pending
     */
    abstract ArrayList<Session> getAllSessionsWhereDiverIsInvited(Diver diver, boolean pending);

    /**
     * @param session the session to create
     * @return if the session is created
     */
    abstract boolean createSession(Session session);

    /**
     * @param session the session to modify
     * @return if the session is modified
     */
    abstract boolean modifySession(Session session);

    /**
     * @param session the session to delete
     * @return if the session is deleted
     */
    abstract boolean deleteSession(Session session);

    /**
     * @param session the session to invite
     * @param receiver the receiver of the invitation
     * @return the invitation
     */
    abstract Invitation getInvitation(Session session, Diver receiver);

    /**
     * @param receiver the receiver
     * @return all the invitations to the diver
     */
    public abstract ArrayList<Invitation> getAllInvitationsToDiver(Diver receiver);

    /**
     * @param session the session
     * @return all the invitations to the session
     */
    abstract ArrayList<Invitation> getAllInvitationsToSession(Session session);

    /**
     * @param invitation the invitation to create
     * @return if the invitation is created
     */
    abstract boolean createInvitation(Invitation invitation);

    /**
     * @param invitation the invitation to accept
     * @return if the invitation is accepted
     */
    abstract boolean acceptInvitation(Invitation invitation);

    /**
     * @param invitation the invitation to refuse/delete
     * @return if the invitation is refused/deleted
     */
    public abstract boolean deleteInvitation(Invitation invitation);

}
