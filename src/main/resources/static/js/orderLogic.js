/**
 * needs jquery.min.js
 */

//finished
function postOrder(form, shop, table) {
	form.submit(function(e) {
		e.preventDefault();
		var shopvalue = "\"shop\":\"" + shop + "\",";
		var obj = "{";
		obj += '"orderPos":[';
		var arr = form.serializeArray();
		console.log(JSON.stringify(arr));
		for (var i = 0; i < arr.length; i++) {
			obj += '{';
			obj += '"item":{';
			obj += shopvalue;
			obj += '"name":"'
			obj += arr[i].name;
			obj += '"';
			obj += '},';
			obj += '"amount":"';
			obj += arr[i].value;
			obj += '"';
			obj += '}';
			if (i < arr.length - 1) {
				obj += ',';
			}
		}
		obj += ']}'
		console.log("Hi");
		var xhr = $.ajax({
			"url": "/api/shop/" + shop + "/table/" + table + "/order",
			"type": "POST",
			"dataType": "json",
			"contentType": "application/json",
			"data": obj,
			success: function(result) {
				alert(xhr.getResponseHeader("Order"));
				window.location.reload();
			},
			error: function(result) {
				alert(xhr.getResponseHeader("Order"));
			}
		});
	});
}

//finished
/**
 * table_nr and from timestamp not used yet!
 */
function refreshOrders(data_table, shop, table_nr, from, set_delay) {
	callout = function() {
		$.ajax({
			"url": "/api/shop/" + shop + "/table/-1/order?pageSize=20",
			"type": "GET",
			"dataType": "json",
			"contentType": "application/json",
			"data": null
		})
			.fail(function(jqXHR, textStatus, errorThrown) {
				alert("Connection to ordering service lost");
			})
			.done(function(response) {
				var event_data = '';
				data_table.find("tr:gt(0)").remove();;
				$.each(response, function(index, value) {
					if (value.isOpen == true) {
						if (index % 2 == 0) {
							event_data += '<tr onclick="showAlert(' + value.nr + ')" style="cursor: pointer" bgcolor="#D8D8D8">';
						} else {
							event_data += '<tr onclick="showAlert(' + value.nr + ')" style="cursor: pointer" bgcolor="white">';
						}
					} else if (value.isOpen == false) {
						event_data += '<tr onclick="showAlert(' + value.nr + ')" style="cursor: pointer" bgcolor="lightsalmon">';
					}
					event_data += '<td>' + value.nr + '</td>';
					event_data += '<td>' + value.table.name + '</td>';

					event_data += '<td>';
					$.each(value.orderPos, function(i, val) {
						event_data += val.item.name + ': ' + val.amount + '\n';
					});
					event_data += '</td>';
					event_data += '<td>' + value.price + ' €' + '</td>';
					event_data += '';
					event_data += '';
					event_data += '</tr>';
				});
				data_table.append(event_data);
			})
	};
	callout();
	setInterval(function() {
		callout();
	}, set_delay);
}


/**
 * finished
 */
// delete logic
function showAlert(index) {
	if (confirm("Do you want to delete the order with id: " + index + "?")) {
		var xhr = $.ajax({
			"url": "/api/" + document.getElementById("shop").innerHTML + "/order/" + index,
			"type": "DELETE",
			"dataType": "json",
			"contentType": "application/json",
			"data": null,
			success: function(result) {
				alert(xhr.getResponseHeader("Order"));
			},
			error: function(result) {
				alert(result.getResponseHeader("Order"));
			}
		});
	}
}