$(function () {
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		idb:{
			tableName: null,
			excelFieldUrl: null,
			modelFieldUrl: null
		},
		reqmsg:null
	},
	methods: {
		reload: function () {
			vm.idb.tableName = null
			vm.idb.excelFieldUrl = null
			vm.idb.modelFieldUrl = null
		},
		productSQL: function() {
			if(vm.idb.tableName == null){
				return alert("表名不能为空!");
			}
			if(vm.idb.excelFieldUrl == null){
				return alert("Excel文件全路径不能为空!");
			}
			if(vm.idb.modelFieldUrl == null){
				return alert("XML文件全路径不能为空!");
			}
			
			var url = "/sys/import/sql";
			var data = JSON.stringify(vm.idb);
			$.post ( url ,data, function ( msg ) { 
				vm.reqmsg = msg;
			} , "json" );
			
		}
	}
});

