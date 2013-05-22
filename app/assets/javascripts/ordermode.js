function enableOrderMode() {
	$(".ordermode_checkbox").change(function () {
		$(this).parent().parent().toggleClass('ordered');
	});
}

function ordermode(enabled) {
	if (enabled) {
		$('#ordertable tr:first-child').prepend('<th class="ordermode_remove">Bestilt</th>');
		$('#ordertable tr:not(:first-child)').prepend('<td class="ordermode_remove"><input class="ordermode_checkbox" type="checkbox"></td>');
		enableOrderMode();
	} else {
		$('.ordermode_remove').remove();
		$('.ordered').removeClass('ordered');
	}
}

$("#ordermode").change(function () {
	if ($(this).is(':checked')) {
		ordermode(true);
	} else {
		ordermode(false);
	}
});