package com.ntg.engine.entites;
/**
*@ModifaiedBy:Aya.ramadan =>Dev-00000521 : sla millstone bugs
*/

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "adm_schedule_con")
public class Schedule {

    @Id
    @SequenceGenerator(allocationSize = 1, name = "adm_schedule_engine_s", sequenceName = "adm_schedule_engine_s", initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_schedule_engine_s")
    @Column(name = "recid", nullable = false)
    private Long recId;

    @Column(name = "crone_exp", nullable = true)

    private String croneExp;

    /* recurring part */
    @Column(name = "recurring_Unit", nullable = true)
    private Long recurringUnit;

    @Column(name = "recurring_value", nullable = true)
    private Long recurringValue;

    @Column(name = "recurring_type", nullable = true)
    private Long recurringType;

    /* mint part */
    @Column(name = "mint_type", nullable = false)
    private Long selectedCronforMinute;

    @Column(name = "every_mint", nullable = true)
    private Long everyMint;

    @Column(name = "every_mint_start_at", nullable = true)
    private Long everyMintStartAt;

    @Column(name = "first_mint_between", nullable = true)
    private Long firstMintBetween;

    @Column(name = "last_mint_between", nullable = true)
    private Long lastMintBetween;

    @Column(name = "Specfic_mint", nullable = true)
    private String selectedSpecMin;

    /* hour part */
    @Column(name = "hour_type", nullable = false)
    private Long selectedCronforHour;

    @Column(name = "every_hour", nullable = true)
    private Long everyHour;

    @Column(name = "every_hour_start_at", nullable = true)
    private Long everyHourStartAt;

    @Column(name = "first_hour_between", nullable = true)
    private Long firstHourBetween;

    @Column(name = "last_hour_between", nullable = true)
    private Long lastHourBetween;

    @Column(name = "Specfic_hour", nullable = true)
    private String selectedSpecHour;

    /* day part */
    @Column(name = "day_type", nullable = false)
    private Long selectedCronforDay;

    @Column(name = "every_day", nullable = true)
    private Long everyDay;

    @Column(name = "every_day_start_at", nullable = true)
    private Long everyDayStartAt;

    @Column(name = "every_day_of_month", nullable = true)
    private Long everyDayOfMonth;

    @Column(name = "every_day_of_month_start_at", nullable = true)
    private Long everyDayOfMonthStartAt;

    @Lob
    @Column(name = "condition", nullable = true)
    private String addCondition;

    @ManyToOne
    @JoinColumn(name = "action_id", nullable = true)
    private ScheduleAction scheduleAction;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schedule", fetch = FetchType.EAGER)
    private List<SlaMilestones> milestones;

    //Email Part
    @ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "EMAIL_TEMPLATE_RECID") ,name = "template_id", nullable = true)
    private EmailTemplate emailTemplate;
    /**
     * @ِAddedBy:Aya.Ramadan as ToEmail and CCEmail moved from emailTemplate object
     * to schedule object
     */

    @Column(name = "dynamic_mail")
    @ColumnDefault("'0'")
    private Boolean dynamicEmail;


    @Column(name = "static_mail")
    @ColumnDefault("'0'")
    private Boolean staticEmail;


    @Lob
    @Column(name = "to_email", nullable = true)
    private String toEmail;

    @Lob
    @Column(name = "cc_email", nullable = true)
    private String ccEmail;

    @Column(name = "to_dynamic_mail_uda", nullable = true)
    private String toDynamicMailUda;

    @Column(name = "cc_dynamic_mail_uda", nullable = true)
    private String ccDynamicMailUda;
    ///@Aya.Ramadan Dev-00000521 : sla millstone bugs
	@Column (name = "is_deleted")
	@ColumnDefault("'0'")
	private Boolean isDeleted ;
	
	/////////////////////////////

//    @ManyToOne
//    @JoinColumn(name = "type_id")
//    private Types type;

    @Column(name = "Specfic_day", nullable = true)
    private String selectedSpecDay;

    @Column(name = "Specfic_dayofmonth", nullable = true)
    private String selectedSpecDayOfMonth;

    /* month part */
    @Column(name = "month_type", nullable = false)
    private Long selectedCronforMonth;

    @Column(name = "every_month", nullable = true)
    private Long everyMonth;

    @Column(name = "every_month_start_at", nullable = true)
    private Long everyMonthStartAt;

    @Column(name = "first_month_between", nullable = true)
    private Long firstMonthBetween;

    @Column(name = "last_month_between", nullable = true)
    private Long lastMonthBetween;

    @Column(name = "Specfic_month", nullable = true)
    private String selectedSpecMonth;

    @Column(name = "type_id", insertable = false, updatable = false)
    private Long typeId;

    @Lob
    @Column(name = "query")
    private String query;

    @Column(name = "job_Running")
    private Boolean jobRunning;

    @Column(name = "Job_Name")
    private String jobName;

    @Column(name = "save_check")
    private Boolean saveCheck;

    @Lob
    @Column(name = "JOIN_TABLES")
    private String joinTables;


    @Column(name = "SLA_RecID")
    private Long slaProfileId;


    @Column(name = "name")
    private String name;
///@Aya.Ramadan Dev-00000521 : sla millstone bugs 
    @Column(name = "table_name")
    private String tableName;
    

    /*********************************************/

    public String getAddCondition() {
        return addCondition;
    }

    public void setAddCondition(String addCondition) {
        this.addCondition = addCondition;
    }

    public ScheduleAction getScheduleAction() {
        return scheduleAction;
    }

    public void setScheduleAction(ScheduleAction scheduleAction) {
        this.scheduleAction = scheduleAction;
    }

    public EmailTemplate getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(EmailTemplate emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    public Boolean isDynamicEmail() {
        return (dynamicEmail == null) ? false : dynamicEmail;
    }

    public void setDynamicEmail(Boolean dynamicEmail) {
        this.dynamicEmail = (dynamicEmail == null) ? false : dynamicEmail;
    }

    public Boolean isStaticEmail() {
        return (staticEmail == null) ? false : staticEmail;
    }

    public void setStaticEmail(Boolean staticEmail) {
        this.staticEmail = (staticEmail == null) ? false : staticEmail;
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

    public Long getRecId() {
        return recId;
    }

    public void setRecId(Long recId) {
        this.recId = recId;
    }

    public Long getSelectedCronforMonth() {
        return selectedCronforMonth;
    }

    public void setSelectedCronforMonth(Long selectedCronforMonth) {
        this.selectedCronforMonth = selectedCronforMonth;
    }

    public Long getSelectedCronforMinute() {
        return selectedCronforMinute;
    }

    public void setSelectedCronforMinute(Long selectedCronforMinute) {
        this.selectedCronforMinute = selectedCronforMinute;
    }

    public Long getEveryMint() {
        return everyMint;
    }

    public void setEveryMint(Long everyMint) {
        this.everyMint = everyMint;
    }

    public Long getEveryMintStartAt() {
        return everyMintStartAt;
    }

    public void setEveryMintStartAt(Long everyMintStartAt) {
        this.everyMintStartAt = everyMintStartAt;
    }

    public Long getFirstMintBetween() {
        return firstMintBetween;
    }

    public void setFirstMintBetween(Long firstMintBetween) {
        this.firstMintBetween = firstMintBetween;
    }

    public Long getLastMintBetween() {
        return lastMintBetween;
    }

    public void setLastMintBetween(Long lastMintBetween) {
        this.lastMintBetween = lastMintBetween;
    }

    public Long getSelectedCronforHour() {
        return selectedCronforHour;
    }

    public void setSelectedCronforHour(Long selectedCronforHour) {
        this.selectedCronforHour = selectedCronforHour;
    }

    public Long getEveryHour() {
        return everyHour;
    }

    public void setEveryHour(Long everyHour) {
        this.everyHour = everyHour;
    }

    public Long getEveryHourStartAt() {
        return everyHourStartAt;
    }

    public void setEveryHourStartAt(Long everyHourStartAt) {
        this.everyHourStartAt = everyHourStartAt;
    }

    public Long getFirstHourBetween() {
        return firstHourBetween;
    }

    public void setFirstHourBetween(Long firstHourBetween) {
        this.firstHourBetween = firstHourBetween;
    }

    public Long getLastHourBetween() {
        return lastHourBetween;
    }

    public void setLastHourBetween(Long lastHourBetween) {
        this.lastHourBetween = lastHourBetween;
    }

    public Long getSelectedCronforDay() {
        return selectedCronforDay;
    }

    public void setSelectedCronforDay(Long selectedCronforDay) {
        this.selectedCronforDay = selectedCronforDay;
    }

    public Long getEveryDay() {
        return everyDay;
    }

    public void setEveryDay(Long everyDay) {
        this.everyDay = everyDay;
    }

    public Long getEveryDayStartAt() {
        return everyDayStartAt;
    }

    public void setEveryDayStartAt(Long everyDayStartAt) {
        this.everyDayStartAt = everyDayStartAt;
    }

    public Long getEveryDayOfMonth() {
        return everyDayOfMonth;
    }

    public void setEveryDayOfMonth(Long everyDayOfMonth) {
        this.everyDayOfMonth = everyDayOfMonth;
    }

    public Long getEveryDayOfMonthStartAt() {
        return everyDayOfMonthStartAt;
    }

    public void setEveryDayOfMonthStartAt(Long everyDayOfMonthStartAt) {
        this.everyDayOfMonthStartAt = everyDayOfMonthStartAt;
    }

    public Long getEveryMonth() {
        return everyMonth;
    }

    public void setEveryMonth(Long everyMonth) {
        this.everyMonth = everyMonth;
    }

    public Long getEveryMonthStartAt() {
        return everyMonthStartAt;
    }

    public void setEveryMonthStartAt(Long everyMonthStartAt) {
        this.everyMonthStartAt = everyMonthStartAt;
    }

    public Long getFirstMonthBetween() {
        return firstMonthBetween;
    }

    public void setFirstMonthBetween(Long firstMonthBetween) {
        this.firstMonthBetween = firstMonthBetween;
    }

    public Long getLastMonthBetween() {
        return lastMonthBetween;
    }

    public void setLastMonthBetween(Long lastMonthBetween) {
        this.lastMonthBetween = lastMonthBetween;
    }

    public int[] getSelectedSpecMin() {
        return ConvertStringToListInit(selectedSpecMin);
    }

    public void setSelectedSpecMin(int[] selectedSpecMin) {
        this.selectedSpecMin = convertIntListToString(selectedSpecMin);
    }

    public int[] getSelectedSpecHour() {
        return ConvertStringToListInit(selectedSpecHour);
    }

    public void setSelectedSpecHour(int[] selectedSpecHour) {
        this.selectedSpecHour = convertIntListToString(selectedSpecHour);
    }

    public int[] getSelectedSpecDay() {
        return ConvertStringToListInit(selectedSpecDay);
    }

    public void setSelectedSpecDay(int[] selectedSpecDay) {
        this.selectedSpecDay = convertIntListToString(selectedSpecDay);
    }

    public int[] getSelectedSpecDayOfMonth() {
        return ConvertStringToListInit(selectedSpecDayOfMonth);
    }

    public void setSelectedSpecDayOfMonth(int[] selectedSpecDayOfMonth) {
        this.selectedSpecDayOfMonth = convertIntListToString(selectedSpecDayOfMonth);
    }

    public int[] getSelectedSpecMonth() {
        return ConvertStringToListInit(selectedSpecMonth);
    }

    public void setSelectedSpecMonth(int[] selectedSpecMonth) {
        this.selectedSpecMonth = convertIntListToString(selectedSpecMonth);
    }

    public String getCroneExp() {
        return croneExp;
    }

    public void setCroneExp(String croneExp) {
        this.croneExp = croneExp;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Boolean isJobRunning() {
        return jobRunning;
    }

    public void setJobRunning(Boolean jobRunning) {
        this.jobRunning = jobRunning;
    }

    public Long getRecurringValue() {
        return recurringValue;
    }

    public void setRecurringValue(Long recurringValue) {
        this.recurringValue = recurringValue;
    }

    public Long getRecurringType() {
        return recurringType;
    }

    public void setRecurringType(Long recurringType) {
        this.recurringType = recurringType;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Boolean getSaveCheck() {
        return saveCheck;
    }

    public void setSaveCheck(Boolean saveCheck) {
        this.saveCheck = saveCheck;
    }

    public Long getRecurringUnit() {
        return recurringUnit;
    }

    public void setRecurringUnit(Long recurringUnit) {
        this.recurringUnit = recurringUnit;
    }

    public String getJoinTables() {
        return joinTables;
    }

    public void setJoinTables(String joinTables) {
        this.joinTables = joinTables;
    }

    public Long getSlaProfileId() {
        return slaProfileId;
    }

    public void setSlaProfileId(Long slaProfileId) {
        this.slaProfileId = slaProfileId;
    }

    public List<SlaMilestones> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<SlaMilestones> milestones) {
        this.milestones = milestones;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String convertIntListToString(int[] list) {
        if (list != null && list.length > 0) {

            String re = String.valueOf(list[0]);
            for (int i = 1; i < list.length; i++) {
                re += "," + list[i];
            }
            return re;
        } else {
            return null;
        }
    }

    int[] ConvertStringToListInit(String value) {
        if (value != null) {
            String[] list = value.split(",");
            int[] re = new int[list.length];
            for (int i = 0; i < re.length; i++) {
                re[i] = Integer.valueOf(list[i]);

            }
            return re;
        }

        return null;
    }
///@Aya.Ramadan Dev-00000521 : sla millstone bugs
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Boolean getDynamicEmail() {
		return dynamicEmail;
	}

	public Boolean getStaticEmail() {
		return staticEmail;
	}
    
	
    
}
