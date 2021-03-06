/**
 * ﻿Copyright (C) 2013-2014 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License version 2 as publishedby the Free
 * Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of the
 * following licenses, the combination of the program with the linked library is
 * not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed under
 * the aforementioned licenses, is permitted by the copyright holders if the
 * distribution is compliant with both the GNU General Public License version 2
 * and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */
package org.n52.io;

import java.util.Map;

import org.joda.time.Interval;
import org.n52.io.crs.BoundingBox;
import org.n52.io.img.ChartDimension;
import org.n52.io.v1.data.StyleProperties;
import org.n52.web.BadRequestException;
import org.n52.web.WebException;
import org.springframework.util.MultiValueMap;

/**
 * Delegates IO parameters to an {@link IoParameters} instance by composing parameter access with Web
 * exception handling.
 */
public final class QueryParameters extends IoParameters {

    /**
     * Creates an simple view on given query. The {@link MultiValueMap} is flattened to a single value map.
     * 
     * @param query
     *        the incoming query parameters.
     * @return a query parameters instance handling Web exceptions.
     * @see WebException
     */
    public static IoParameters createFromQuery(MultiValueMap<String, String> query) {
        return createFromQuery(query.toSingleValueMap());
    }
    
    /**
     * Creates an simple view on given query.
     * 
     * @param query
     *        the incoming query parameters.
     * @return a query parameters instance handling Web exceptions.
     * @see WebException
     */
    public static IoParameters createFromQuery(Map<String, String> query) {
        return new QueryParameters(query);
    }

    private QueryParameters(Map<String, String> query) {
        super(query);
    }

    public int getOffset() {
        try {
            return super.getOffset();
        }
        catch (IoParseException e) {
            throw new BadRequestException("Bad '" + OFFSET + "' parameter.", e);
        }
    }

    public int getLimit() {
        try {
            return super.getLimit();
        }
        catch (IoParseException e) {
            throw new BadRequestException("Bad '" + LIMIT + "' parameter.", e);
        }
    }

    public ChartDimension getChartDimension() {
        try {
            return super.getChartDimension();
        }
        catch (IoParseException e) {
            throw new BadRequestException("Bad '" + WIDTH + "' or '" + HEIGHT + "' parameter(s).", e);
        }
    }

    public boolean isBase64() {
        try {
            return super.isBase64();
        }
        catch (IoParseException e) {
            throw new BadRequestException("Bad '" + BASE_64 + "' parameter.", e);
        }
    }

    public boolean isGrid() {
        try {
            return super.isGrid();
        }
        catch (IoParseException e) {
            throw new BadRequestException("Bad '" + GRID + "' parameter.", e);
        }
    }

    public boolean isGeneralize() {
        try {
            return super.isGeneralize();
        }
        catch (IoParseException e) {
            throw new BadRequestException("Bad '" + GENERALIZE + "' parameter.", e);
        }
    }

    public boolean isLegend() {
        try {
            return super.isLegend();
        }
        catch (IoParseException e) {
            throw new BadRequestException("Bad '" + LEGEND + "' parameter.", e);
        }
    }

    public String getLocale() {
        return super.getLocale();
    }

    public StyleProperties getStyle() {
        try {
            return super.getStyle();
        }
        catch (IoParseException e) {
            throw new BadRequestException("Could not read '" + STYLE + "' property.", e);
        }
    }

    public String getFormat() {
        return super.getFormat();
    }

    public Interval getTimespan() {
        try {
            return super.getTimespan();
        }
        catch (IoParseException e) {
            BadRequestException badRequest = new BadRequestException("Invalid timespan.", e);
            badRequest.addHint("Valid timespans have to be in ISO8601 period format.");
            badRequest.addHint("Valid examples: 'PT6H/2013-08-13TZ' or '2013-07-13TZ/2013-08-13TZ'.");
            throw badRequest;
        }
    }

    public BoundingBox getSpatialFilter() {
        try {
            return super.getSpatialFilter();
        }
        catch (IoParseException e) {
            BadRequestException ex = new BadRequestException("Spatial filter could not be determined.", e);
            ex.addHint("Refer to the API documentation and check the parameter against required syntax!");
            ex.addHint("Check http://epsg-registry.org for EPSG CRS definitions and codes.");
            throw ex;
        }
    }

    public boolean isForceXY() {
        try {
            return super.isForceXY();
        }
        catch (IoParseException e) {
            throw new BadRequestException("Bad '" + FORCE_XY + "' parameter.", e);
        }
    }

    public boolean isExpanded() {
        try {
            return super.isExpanded();
        }
        catch (IoParseException e) {
            throw new BadRequestException("Bad '" + EXPANDED + "' parameter.", e);
        }
    }
    
    public boolean isForceLatestValueRequests() {
        try {
            return super.isForceLatestValueRequests();
        } catch (IoParseException e) {
            throw new BadRequestException("Bad '" + FORCE_LATEST_VALUE + "' parameter", e);
        }
    }

}
