package com.transporter.entities.user.inbox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInboxRepository extends JpaRepository<UserInbox, Long> {

    public UserInbox findByUserId(Long userId);
}
