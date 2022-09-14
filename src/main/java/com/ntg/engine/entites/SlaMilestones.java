package com.ntg.engine.entites;
/**
 * @ModifaiedBy:Aya.ramadan =>Dev-00000521 : sla millstone bugs
 */

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;


@Entity
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "adm_Sla_Milestone")
public class SlaMilestones implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2451136711701674903L;
    @Id
    @SequenceGenerator(allocationSize = 1, name = "adm_Sla_Mileston_s", sequenceName = "adm_Sla_Mileston_s", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_Sla_Mileston_s")
    @Column(name = "recid", nullable = false)
    private long recId;

    @Column(name = "from_range")
    public Long fromRange;

    @Column(name = "to_range")
    public Long toRange;

    @Column(name = "title")
    private String title;

    @Column(name = "applyed_action_id")
    public Long applyedActionId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "EMAIL_TEMPLATE_RECID"), name = "template_id", nullable = true)
    private EmailTemplate emailTemplate;

    @Column(name = "save_after_do_rule", nullable = true)
    private Boolean saveAfterDoRule;

    @Column(name = "action_query", nullable = true)
    @Lob
    public String applyedActionQuery;


    // @ManyToOne
    @Column(name = "sla_Profile_Id")
    private Long slaProfile;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "SCHEDULE_RECID"), name = "config_Id", referencedColumnName = "recId")
    private Schedule schedule;
    @Column(name = "to_email", nullable = true)
    @Lob
    public String toEmail;

    @Column(name = "cc_email", nullable = true)
    @Lob
    public String ccEmail;


    /**
     * @ِAddedBy:Aya.Ramadan as ToEmail and CCEmail moved from emailTemplate object
     *                       to schedule object
     */
    @Column(name = "dynamic_mail")
    @ColumnDefault("'0'")
    private Boolean dynamicEmail;

    @Column(name = "static_mail")
    @ColumnDefault("'0'")
    private Boolean staticEmail;

    @Column(name = "to_dynamic_mail_uda", nullable = true)
    private String toDynamicMailUda;

    @Column(name = "cc_dynamic_mail_uda", nullable = true)
    private String ccDynamicMailUda;
    ///@Aya.Ramadan Dev-00000521 : sla millstone bugs
    @Column(name = "is_deleted")
    @ColumnDefault("'0'")
    private Boolean isDeleted;

    public String getApplyedActionQuery() {
        return applyedActionQuery;
    }

    public void setApplyedActionQuery(String applyedActionQuery) {
        this.applyedActionQuery = applyedActionQuery;
    }

    public Long getFromRange() {
        return fromRange;
    }

    public void setFromRange(Long fromRange) {
        this.fromRange = fromRange;
    }

    public Long getToRange() {
        return toRange;
    }

    public void setToRange(Long toRange) {
        this.toRange = toRange;
    }

    public Long getApplyedActionId() {
        return applyedActionId;
    }

    public void setApplyedActionId(Long applyedActionId) {
        this.applyedActionId = applyedActionId;
    }

    public EmailTemplate getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(EmailTemplate emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    public Boolean getSaveAfterDoRule() {
        return saveAfterDoRule;
    }

    public void setSaveAfterDoRule(Boolean saveAfterDoRule) {
        this.saveAfterDoRule = saveAfterDoRule;
    }

    public long getRecId() {
        return recId;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getCcEmail() {
        return ccEmail;
    }

    public void setCcEmail(String ccEmail) {
        this.ccEmail = ccEmail;
    }

    public void setRecId(long recId) {
        this.recId = recId;
    }

    public Long getSlaProfile() {
        return slaProfile;
    }

    public void setSlaProfile(Long slaProfile) {
        this.slaProfile = slaProfile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Boolean getDynamicEmail() {
        return (dynamicEmail == null) ? false : dynamicEmail;
    }

    public void setDynamicEmail(Boolean dynamicEmail) {
        this.dynamicEmail = dynamicEmail;
    }

    public Boolean getStaticEmail() {
        return (staticEmail == null) ? false : staticEmail;
    }

    public void setStaticEmail(Boolean staticEmail) {
        this.staticEmail = staticEmail;
    }

    public String getToDynamicMailUda() {
        return toDynamicMailUda;
    }

    public void setToDynamicMailUda(String toDynamicMailUda) {
        this.toDynamicMailUda = toDynamicMailUda;
    }

    public String getCcDynamicMailUda() {
        return ccDynamicMailUda;
    }

    public void setCcDynamicMailUda(String ccDynamicMailUda) {
        this.ccDynamicMailUda = ccDynamicMailUda;
    }

    ///@Aya.Ramadan Dev-00000521 : sla millstone bugs
    public Boolean getIsDeleted() {
        if (isDeleted == null)
            return false;

        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
