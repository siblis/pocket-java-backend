package ru.geekbrains.pocket.backend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.VerificationToken;

import java.util.Date;
import java.util.stream.Stream;

public interface VerificationTokenRepository extends MongoRepository<VerificationToken, ObjectId> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

//    @Modifying
//    @Query("delete from VerificationToken t where t.expiryDate <= ?1")
//    void deleteAllExpiredSince(Date now);

}
