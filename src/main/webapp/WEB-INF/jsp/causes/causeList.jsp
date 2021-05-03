<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="causes">
	<h2>Causas en curso</h2>
	<table id="causesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Nombre</th>
				<th>Total recaudado</th>
				<th>Objetivo</th>
				<th>Opciones</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${causes}" var="cause">
				<tr>
					<td><c:out value="${cause.name}" /></td>
					<td><c:out value="${cause.gathered}" /></td>
					<td><c:out value="${cause.target}" /></td>
					<td><sec:authorize access="hasAuthority('owner')">
							<spring:url value="/causes/{causeId}/donation" var="donationUrl">
								<spring:param name="causeId" value="${cause.id}" />
							</spring:url>
							<a href="${fn:escapeXml(donationUrl)}" class="btn btn-default">Donar</a>
						</sec:authorize> <spring:url value="/causes/{causeId}/details" var="detailsUrl">
							<spring:param name="causeId" value="${cause.id}" />
						</spring:url> <a href="${fn:escapeXml(detailsUrl)}" class="btn btn-default">Ver
							detalles</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<h2>Causas terminadas</h2>
	<table id="causesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Nombre</th>
				<th>Total recaudado</th>
				<th>Objetivo</th>
				<th>Opciones</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${completedCauses}" var="cause">
				<tr>
					<td><c:out value="${cause.name}" /></td>
					<td><c:out value="${cause.gathered}" /></td>
					<td><c:out value="${cause.target}" /></td>
					<td><spring:url value="/causes/{causeId}/details"
							var="detailsUrl">
							<spring:param name="causeId" value="${cause.id}" />
						</spring:url> <a href="${fn:escapeXml(detailsUrl)}" class="btn btn-default">Ver
							detalles</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>