package pt.sensae.services.notification.management.backend.infrastructure.persistence.postgres.model.addressee;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("addressee_config")
public class AddresseePostgres {

    @Id
    public Long persistenceId;

    @Column("id")
    public String id;

    @Column("category")
    public String category;

    @Column("sub_category")
    public String subCategory;

    @Column("level")
    public String level;

    @Column("send_sms")
    public boolean sendSms;

    @Column("send_email")
    public boolean sendEmail;

    @Column("send_notification")
    public boolean sendNotification;
}
