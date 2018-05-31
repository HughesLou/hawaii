/*
 * Copyright (c) 2015. Standard Chartered Bank. All rights reserved.
 */

package com.hughes;

/**
 * Description:
 * Author: 1466811
 * Date:   3:37 PM 8/2/15
 */
public enum TimePeriod {
    /*MINUTE(Dimension.MINUTE,
            (from,
             to) -> Minutes.minutesBetween(from, to).getMinutes() + 1,
            Minutes::minutes,
            from -> from.withZone(DateTimeZone.UTC)
                    .withSecondOfMinute(0)
                    .withMillisOfSecond(0)),
    HOUR(Dimension.HOUR,
            (from,
             to) -> Hours.hoursBetween(from, to).getHours() + 1,
            Hours::hours,
            from -> from.withZone(DateTimeZone.UTC)
                    .withMinuteOfHour(0)
                    .withSecondOfMinute(0)
                    .withMillisOfSecond(0)),
    DAY(Dimension.DAY,
            (from,
             to) -> Days.daysBetween(from, to).getDays() + 1,
            Days::days,
            from -> from.withZone(DateTimeZone.UTC)
                    .withTimeAtStartOfDay()),
    WEEK(Dimension.WEEK,
            (from,
             to) -> Weeks.weeksBetween(from, to).getWeeks() + 1,
            Weeks::weeks,
            from -> from.withZone(DateTimeZone.UTC)
                    .withDayOfWeek(1)
                    .withTimeAtStartOfDay()),
    MONTH(Dimension.MONTH,
            (from,
             to) -> Months.monthsBetween(from, to).getMonths() + 1,
            Months::months,
            from -> from.withZone(DateTimeZone.UTC)
                    .withDayOfMonth(1)
                    .withTimeAtStartOfDay());
    
    private Dimension<Timestamp> dimension;
    private BiFunction<DateTime, DateTime, Integer> getNumberOfPoints;
    private Function<Integer, ReadablePeriod> getPeriodFromNbOfInterval;
    private Function<DateTime, DateTime> getStartOfInterval;
    
    private TimePeriod(Dimension<Timestamp> dimension,
                       BiFunction<DateTime, DateTime, Integer> getNumberOfPoints,
                       Function<Integer, ReadablePeriod> getPeriodFromNbOfInterval,
                       Function<DateTime, DateTime> getStartOfInterval)
    {
        this.dimension = dimension;
        this.getNumberOfPoints = getNumberOfPoints;
        this.getPeriodFromNbOfInterval = getPeriodFromNbOfInterval;
        this.getStartOfInterval = getStartOfInterval;
    }
    
    public Dimension<Timestamp> getDimension()
    {
        return dimension;
    }
    
    public int getNumberOfPoints(DateTime from,
                                 DateTime to)
    {
        return getNumberOfPoints.apply(from, to);
    }
    
    public ReadablePeriod getPeriodFromNbOfInterval(int nbOfInterval)
    {
        return getPeriodFromNbOfInterval.apply(nbOfInterval);
    }
    
    public DateTime getStartOfInterval(DateTime from)
    {
        return getStartOfInterval.apply(from);
    }*/
}