var aMapDialogIdx;
function showSelectMap(longitude, latitude, address, callback,isNotShowSelect) {
	console.log(isNotShowSelect);
	if (aMapDialogIdx == undefined) {
		$.get("aMap/index.do", {
			longitude : longitude,
			latitude : latitude,
			address : address,
			callback : callback,
			isNotShowSelect:isNotShowSelect
		}, function(data) {
			aMapDialogIdx = layer.open({
				type : 1,
				id : 'aMapDialogLayer',
				title : "地图",
				area : [ '900px', '543px' ],
				closeBtn : 1,
				content : data,
				maxmin : true,
				resize : true,
				end: function(index){
					aMapDialogIdx = undefined;
					return true;
				}
			});
		})
	}

}

function closeAmapDialog() {
	if (aMapDialogIdx != undefined) {
		layer.close(aMapDialogIdx);
		aMapDialogIdx = undefined;
	}
}