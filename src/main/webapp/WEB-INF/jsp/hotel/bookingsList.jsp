<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">
    <h2>Hotel Bookings</h2>

    <table id="ownersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 150px;">Pet Name</th>
            <th style="width: 150px;">Start Date</th>
            <th style="width: 150px;">End Date</th>
        </tr>
        </thead>
        <tbody>


			<c:forEach items="${hotels}" var="hotel">
				<tr>
					<td><c:out value="${hotel.owner.firstName}" /></td>
					<td><c:out value="${hotel.pet}" /></a></td>
					<td><c:out value="${hotel.startDate}" /></a></td>
					<td><c:out value="${hotel.endDate}" /></td>


				</tr>

			</c:forEach>


			
			<%--  <h3>Hotel: ${hotel}</h3> --%>
		</tbody>
    </table>
    			
			<spring:url value="/hotel/new" var="newBooking">
			</spring:url>
			<a class="btn btn-default" href="${fn:escapeXml(newBooking)}">New</a>
</petclinic:layout>

