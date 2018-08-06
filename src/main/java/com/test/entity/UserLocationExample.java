package com.test.entity;

import java.util.ArrayList;
import java.util.List;

public class UserLocationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserLocationExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andUlidIsNull() {
            addCriterion("ulid is null");
            return (Criteria) this;
        }

        public Criteria andUlidIsNotNull() {
            addCriterion("ulid is not null");
            return (Criteria) this;
        }

        public Criteria andUlidEqualTo(String value) {
            addCriterion("ulid =", value, "ulid");
            return (Criteria) this;
        }

        public Criteria andUlidNotEqualTo(String value) {
            addCriterion("ulid <>", value, "ulid");
            return (Criteria) this;
        }

        public Criteria andUlidGreaterThan(String value) {
            addCriterion("ulid >", value, "ulid");
            return (Criteria) this;
        }

        public Criteria andUlidGreaterThanOrEqualTo(String value) {
            addCriterion("ulid >=", value, "ulid");
            return (Criteria) this;
        }

        public Criteria andUlidLessThan(String value) {
            addCriterion("ulid <", value, "ulid");
            return (Criteria) this;
        }

        public Criteria andUlidLessThanOrEqualTo(String value) {
            addCriterion("ulid <=", value, "ulid");
            return (Criteria) this;
        }

        public Criteria andUlidLike(String value) {
            addCriterion("ulid like", value, "ulid");
            return (Criteria) this;
        }

        public Criteria andUlidNotLike(String value) {
            addCriterion("ulid not like", value, "ulid");
            return (Criteria) this;
        }

        public Criteria andUlidIn(List<String> values) {
            addCriterion("ulid in", values, "ulid");
            return (Criteria) this;
        }

        public Criteria andUlidNotIn(List<String> values) {
            addCriterion("ulid not in", values, "ulid");
            return (Criteria) this;
        }

        public Criteria andUlidBetween(String value1, String value2) {
            addCriterion("ulid between", value1, value2, "ulid");
            return (Criteria) this;
        }

        public Criteria andUlidNotBetween(String value1, String value2) {
            addCriterion("ulid not between", value1, value2, "ulid");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(String value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(String value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(String value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(String value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(String value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(String value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLike(String value) {
            addCriterion("uid like", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotLike(String value) {
            addCriterion("uid not like", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<String> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<String> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(String value1, String value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(String value1, String value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andOpenIdIsNull() {
            addCriterion("open_id is null");
            return (Criteria) this;
        }

        public Criteria andOpenIdIsNotNull() {
            addCriterion("open_id is not null");
            return (Criteria) this;
        }

        public Criteria andOpenIdEqualTo(String value) {
            addCriterion("open_id =", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotEqualTo(String value) {
            addCriterion("open_id <>", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdGreaterThan(String value) {
            addCriterion("open_id >", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdGreaterThanOrEqualTo(String value) {
            addCriterion("open_id >=", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdLessThan(String value) {
            addCriterion("open_id <", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdLessThanOrEqualTo(String value) {
            addCriterion("open_id <=", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdLike(String value) {
            addCriterion("open_id like", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotLike(String value) {
            addCriterion("open_id not like", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdIn(List<String> values) {
            addCriterion("open_id in", values, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotIn(List<String> values) {
            addCriterion("open_id not in", values, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdBetween(String value1, String value2) {
            addCriterion("open_id between", value1, value2, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotBetween(String value1, String value2) {
            addCriterion("open_id not between", value1, value2, "openId");
            return (Criteria) this;
        }

        public Criteria andLocationXIsNull() {
            addCriterion("location_x is null");
            return (Criteria) this;
        }

        public Criteria andLocationXIsNotNull() {
            addCriterion("location_x is not null");
            return (Criteria) this;
        }

        public Criteria andLocationXEqualTo(String value) {
            addCriterion("location_x =", value, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXNotEqualTo(String value) {
            addCriterion("location_x <>", value, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXGreaterThan(String value) {
            addCriterion("location_x >", value, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXGreaterThanOrEqualTo(String value) {
            addCriterion("location_x >=", value, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXLessThan(String value) {
            addCriterion("location_x <", value, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXLessThanOrEqualTo(String value) {
            addCriterion("location_x <=", value, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXLike(String value) {
            addCriterion("location_x like", value, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXNotLike(String value) {
            addCriterion("location_x not like", value, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXIn(List<String> values) {
            addCriterion("location_x in", values, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXNotIn(List<String> values) {
            addCriterion("location_x not in", values, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXBetween(String value1, String value2) {
            addCriterion("location_x between", value1, value2, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXNotBetween(String value1, String value2) {
            addCriterion("location_x not between", value1, value2, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationYIsNull() {
            addCriterion("location_y is null");
            return (Criteria) this;
        }

        public Criteria andLocationYIsNotNull() {
            addCriterion("location_y is not null");
            return (Criteria) this;
        }

        public Criteria andLocationYEqualTo(String value) {
            addCriterion("location_y =", value, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYNotEqualTo(String value) {
            addCriterion("location_y <>", value, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYGreaterThan(String value) {
            addCriterion("location_y >", value, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYGreaterThanOrEqualTo(String value) {
            addCriterion("location_y >=", value, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYLessThan(String value) {
            addCriterion("location_y <", value, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYLessThanOrEqualTo(String value) {
            addCriterion("location_y <=", value, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYLike(String value) {
            addCriterion("location_y like", value, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYNotLike(String value) {
            addCriterion("location_y not like", value, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYIn(List<String> values) {
            addCriterion("location_y in", values, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYNotIn(List<String> values) {
            addCriterion("location_y not in", values, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYBetween(String value1, String value2) {
            addCriterion("location_y between", value1, value2, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYNotBetween(String value1, String value2) {
            addCriterion("location_y not between", value1, value2, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationBdXIsNull() {
            addCriterion("location_bd_x is null");
            return (Criteria) this;
        }

        public Criteria andLocationBdXIsNotNull() {
            addCriterion("location_bd_x is not null");
            return (Criteria) this;
        }

        public Criteria andLocationBdXEqualTo(String value) {
            addCriterion("location_bd_x =", value, "locationBdX");
            return (Criteria) this;
        }

        public Criteria andLocationBdXNotEqualTo(String value) {
            addCriterion("location_bd_x <>", value, "locationBdX");
            return (Criteria) this;
        }

        public Criteria andLocationBdXGreaterThan(String value) {
            addCriterion("location_bd_x >", value, "locationBdX");
            return (Criteria) this;
        }

        public Criteria andLocationBdXGreaterThanOrEqualTo(String value) {
            addCriterion("location_bd_x >=", value, "locationBdX");
            return (Criteria) this;
        }

        public Criteria andLocationBdXLessThan(String value) {
            addCriterion("location_bd_x <", value, "locationBdX");
            return (Criteria) this;
        }

        public Criteria andLocationBdXLessThanOrEqualTo(String value) {
            addCriterion("location_bd_x <=", value, "locationBdX");
            return (Criteria) this;
        }

        public Criteria andLocationBdXLike(String value) {
            addCriterion("location_bd_x like", value, "locationBdX");
            return (Criteria) this;
        }

        public Criteria andLocationBdXNotLike(String value) {
            addCriterion("location_bd_x not like", value, "locationBdX");
            return (Criteria) this;
        }

        public Criteria andLocationBdXIn(List<String> values) {
            addCriterion("location_bd_x in", values, "locationBdX");
            return (Criteria) this;
        }

        public Criteria andLocationBdXNotIn(List<String> values) {
            addCriterion("location_bd_x not in", values, "locationBdX");
            return (Criteria) this;
        }

        public Criteria andLocationBdXBetween(String value1, String value2) {
            addCriterion("location_bd_x between", value1, value2, "locationBdX");
            return (Criteria) this;
        }

        public Criteria andLocationBdXNotBetween(String value1, String value2) {
            addCriterion("location_bd_x not between", value1, value2, "locationBdX");
            return (Criteria) this;
        }

        public Criteria andLocationBdYIsNull() {
            addCriterion("location_bd_y is null");
            return (Criteria) this;
        }

        public Criteria andLocationBdYIsNotNull() {
            addCriterion("location_bd_y is not null");
            return (Criteria) this;
        }

        public Criteria andLocationBdYEqualTo(String value) {
            addCriterion("location_bd_y =", value, "locationBdY");
            return (Criteria) this;
        }

        public Criteria andLocationBdYNotEqualTo(String value) {
            addCriterion("location_bd_y <>", value, "locationBdY");
            return (Criteria) this;
        }

        public Criteria andLocationBdYGreaterThan(String value) {
            addCriterion("location_bd_y >", value, "locationBdY");
            return (Criteria) this;
        }

        public Criteria andLocationBdYGreaterThanOrEqualTo(String value) {
            addCriterion("location_bd_y >=", value, "locationBdY");
            return (Criteria) this;
        }

        public Criteria andLocationBdYLessThan(String value) {
            addCriterion("location_bd_y <", value, "locationBdY");
            return (Criteria) this;
        }

        public Criteria andLocationBdYLessThanOrEqualTo(String value) {
            addCriterion("location_bd_y <=", value, "locationBdY");
            return (Criteria) this;
        }

        public Criteria andLocationBdYLike(String value) {
            addCriterion("location_bd_y like", value, "locationBdY");
            return (Criteria) this;
        }

        public Criteria andLocationBdYNotLike(String value) {
            addCriterion("location_bd_y not like", value, "locationBdY");
            return (Criteria) this;
        }

        public Criteria andLocationBdYIn(List<String> values) {
            addCriterion("location_bd_y in", values, "locationBdY");
            return (Criteria) this;
        }

        public Criteria andLocationBdYNotIn(List<String> values) {
            addCriterion("location_bd_y not in", values, "locationBdY");
            return (Criteria) this;
        }

        public Criteria andLocationBdYBetween(String value1, String value2) {
            addCriterion("location_bd_y between", value1, value2, "locationBdY");
            return (Criteria) this;
        }

        public Criteria andLocationBdYNotBetween(String value1, String value2) {
            addCriterion("location_bd_y not between", value1, value2, "locationBdY");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}