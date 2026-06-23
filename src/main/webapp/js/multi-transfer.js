/**
 * 开源代码, 有兴趣的可以在保留声明的前提下免费使用.
 *********** 声明开始 ***********
 * @author ideawu@163.com
 * @link http://www.ideawu.net
 *********** 声明结束 ***********
 *
 * 用于显示数据表格的JavaScript控件.
 * 需要 jQuery.
 *
 * @example:
 *
 * <code>
 * HTML代码:
 * <div id="my_div"></div>
 *
 * JavaScript代码:
 * var table = new TableView('my_div');
 * table.dataKey = 'id';
 * table.header = {
 * 	'id' : 'Id',
 * 	'name' : 'Name',
 * };
 *
 * table.add({id:1, name:'Tom'});
 * table.render();
 * </code>
 *
 * @param string id: HTML节点的id.
 */
function TableView(id){
	this.id = id;
	this.dataKey = '';
	this.header = {};
	this.rows = [];
	this.title = '';
	this.container = null;
	this._rendered = false;

	/**
	 * 确定要显示哪些内部控件.
	 */
	this.display = {
		title : true, // 标题
		count : true, // 计数
		filter : false // 过滤器
	};

	var self = this;

	var div = document.getElementById(this.id);
	div.view = this;
	this.container = div;

	/**
	 * 添加一个数据对象, 会立即影响展现.
	 */
	this.add = function(row){
		var rid = row[self.dataKey];
		if(self.rows[rid] != undefined){
			return;
		}
		self.rows[rid] = row;

		if(self._rendered){
			var str = '';
			str += '<tr class="">';
			str += '<td>';
			str += '<input type="checkbox" value="' + rid + '" />';
			str += '</td>';
			for(var k in self.header){
				str += '<td>';
				str += row[k];
				str += '</td>';
			}
			str += "</tr>\r\n";
			var table = self.container.getElementsByTagName('table')[0];
			$(table).append(str);

			self._after_render();
		}
	};

	/**
	 * 添加数据对象列表, 如果视图已经被渲染, 会立即影响展现.
	 */
	this.addRange = function(rows){
		var str = '';
		for(var i in rows){
			var row = rows[i];
			var rid = row[self.dataKey];
			if(self.rows[rid] != undefined){
				continue;
			}
			self.rows[rid] = row;
			if(self._rendered){
				str += '<tr>';
				str += '<td>';
				str += '<input type="checkbox" value="' + rid + '" />';
				str += '</td>';
				for(var k in self.header){
					str += '<td>';
					str += row[k];
					str += '</td>';
				}
				str += "</tr>\r\n";
			}
		}

		if(str != ''){
			var table = self.container.getElementsByTagName('table')[0];
			$(table).append(str);

			self._after_render();
		}
	};

	/**
	 * 删除一个数据对象, 会立即影响展现.
	 */
	this.del = function(row){
		var rid = row[self.dataKey];
		if(self.rows[rid] == undefined){
			return;
		}
		delete self.rows[rid];

		if(self._rendered){
			var cb = $(self.container).find('input[type=checkbox][value=' + rid + ']')[0];
			var tr = $(cb).parents('tr')[0];
			$(tr).remove();

			self._after_render();
		}
	};

	/**
	 * 删除数据对象列表, 如果视图已经被渲染, 会立即影响展现.
	 */
	this.delRange = function(rows){
		var found = false;
		for(var i in rows){
			var row = rows[i];
			var rid = row[self.dataKey];
			if(self.rows[rid] == undefined){
				continue;
			}
			found = true;
			delete this.rows[rid];
			if(self._rendered){
				var cb = $(self.container).find('input[type=checkbox][value=' + rid + ']')[0];
				var tr = $(cb).parents('tr')[0];
				$(tr).remove();
			}
		}
		if(found){
			self._after_render();
		}
	};

	/**
	 * 内部方法. 用于全选或者取消全选行.
	 */
	this._toggleSelect = function(){
		var c = $(self.container).find('input[type=checkbox]')[0];
		if(c.checked){
			self.selectAll();
		}else{
			self.unselectAll();
		}
	};

	/**
	 * 当行双击时调用, 空方法. 使用者重写本方法, 获取行双击回调.
	 */
	this.dblclick = function(id){
	};
	
	/**
	 * 更新统计数据.
	 */
	this._update_meta = function(){
		if(!self.display.count){
			return;
		}
		var marked_count = 0;
		var row_count = 0;

		var trs = self.container.getElementsByTagName('tr');
		for(var i=1; i<trs.length; i++){
			var tr = trs[i];
			var cb = tr.getElementsByTagName('input')[0];
			if(cb.checked){
				marked_count++;
			}
			row_count++;
		}

		$(self.container).find('.datagrid_meta span.marked_count').html(marked_count);
		$(self.container).find('.datagrid_meta span.row_count').html(row_count);
	};

	/**
	 * 内部方法. 绑定事件, 设置外观.
	 */
	this._after_render = function(){
		/* IE6不支持:visible! */
		//$(self.container).find('tr:visible').slice(1).each(function(i, tr){
		$(self.container).find('tr[TableView_tr_visible!=false]').slice(1).each(function(i, tr){
			var cb = tr.getElementsByTagName('input')[0];

			var clz = i%2==0? 'odd' : 'even';
			$(tr).removeClass('odd even');
			$(tr).addClass(clz);

			// 标记已选的行
			if(cb.checked){
				$(tr).addClass('marked');
			}else{
				$(tr).removeClass('marked');
			}
			cb.onclick = function(){
				cb.checked = !cb.checked;
			};
			tr.onclick = function(){
				cb.checked = !cb.checked;
				if(cb.checked){
					$(tr).addClass('marked');
				}else{
					$(tr).removeClass('marked');
				}
				self._update_meta();
			};
			tr.onmouseover = function(){
				$(tr).addClass('hover');
			};
			tr.onmouseout = function(){
				$(tr).removeClass('hover');
			};
			tr.ondblclick = function(){
				self.dblclick(cb.value);
			};
		});
		
		self._update_meta();
	};

	/**
	 * 渲染整个表格.
	 */
	this.render = function(){
		var str = '';
		str += '<div class="TableView">';
		str += '<div style="margin: 0; padding: 6px 0;" class="datagrid_meta">';
		if(this.display.title){
			str += '<span class="title">' + this.title + '</span>';
		}
		if(this.display.count){
			str += '(<span class="marked_count">0</span>/<span class="row_count">0</span>)';
		}
		if(this.display.filter){
			str += ' <span class="filter"><label>关键字</label>';
			str += '<input type="text" value="" onkeyup="document.getElementById(\'' + this.id + '\').view.filter(this.value)" />';
			str += '</span>';
		}
		str += '</div>';
		str += '<div class="datagrid_div">';
		str += '<table class="datagrid">';
		str += '<tr>';
		str += '<th width="10">';
		str += '<input type="checkbox" value="" onclick="document.getElementById(\'' + this.id + '\').view._toggleSelect()" />';
		str += "</th>\n";
		for(var k in this.header){
			var h = this.header[k];
			str += '<th>';
			str += h;
			str += "</th>\n";
		}
		str += "</tr>\r\n";

		for(var rid in this.rows){
			var row = this.rows[rid];
			str += '<tr>';
			str += '<td>';
			str += '<input type="checkbox" value="' + rid + '" />';
			str += '</td>';
			for(var k in this.header){
				str += '<td>';
				str += row[k];
				str += '</td>';
			}
			str += "</tr>\r\n";
		}
		str += "</table>\n";
		str += "</div><!-- /.datagrid_div -->\n";
		str += "</div><!-- /.TableView -->\n";
		this.container.innerHTML = str;

		this._after_render();
		this._rendered = true;
	};

	/**
	 * 设置所有行的选择标记.
	 */
	this.selectAll = function(){
		var checks = $(self.container).find('input[type=checkbox]');
		for(var i=0; i<checks.length; i++){
			var c = $(checks[i]);
			c.prop('checked',true);
		}
		self._after_render();
	};

	/**
	 * 取消所有行的选择标记.
	 */
	this.unselectAll = function(){
		var ckecks = $(self.container).find('input[type=checkbox]');
		for(var i=0; i<ckecks.length; i++){
			var c = $(ckecks[i]);
			c.prop('checked', false);
		}
		self._after_render();
	};

	/**
	 * 获取所有标记为选择的行对应的数据对象列表.
	 */
	this.getSelected = function(){
		var items = [];
		/* IE6不支持:visible! */
		//$(self.container).find('tr:visible').slice(1).each(function(i, tr){
		$(self.container).find('tr[TableView_tr_visible!=false]').slice(1).each(function(i, tr){
			var cb = $(tr).find('input[type=checkbox]')[0];
			if(cb.checked){
				var key = $(cb).attr('value');
				if(key.length > 0){
					items.push(self.rows[key]);
				}
			}
		});

		return items;
	};

	/**
	 * 获取所有已选择的数据对象键值列表.
	 */
	this.getSelectedKeys = function(){
		var keys = [];
		var rows = self.getSelected();
		for(var i in rows){
			keys.push(rows[i][self.dataKey]);
		}
		return keys;
	};

	/**
	 * 进行模糊过滤.
	 */
	this.filter = function(text){
		var regex = new RegExp(text, 'i');
		$(self.container).find('tr').slice(1).each(function(i, tr){
			tr = $(tr);
			var str = tr.text();
			if(regex.test(str)){
				tr.attr('TableView_tr_visible', 'true');
				tr.show();
			}else{
				tr.attr('TableView_tr_visible', 'false');
				tr.hide();
			}
		});
		self._after_render();
	};

	/**
	 * 清空所有行.
	 */
	this.clear = function(){
		self.rows = [];
		self.render();
	};
}


/**
 * 开源代码, 有兴趣的可以在保留声明的前提下免费使用.
 *********** 声明开始 ***********
 * @author ideawu@163.com
 * @link http://www.ideawu.net
 *********** 声明结束 ***********
 *
 * 用于显示两栏(备选框, 已选框)选择器控件的JavaScript控件.
 * 需要 jQuery, TableView.
 *
 * @example:
 *
 * <code>
 * HTML代码:
 * <div id="sel_div"></div>
 * 
 * JavaScript代码:
 * var sel = new SelectorView('sel_div'); // sel_div 是 HTML 节点 id
 * sel.src.header = {
 * 	id			: 'Id',
 * 	name		: 'Name',
 * 	text		: 'Text'
 * };
 * sel.dst.header = {
 * 	id			: 'Id',
 * 	name		: 'Name',
 * };
 * sel.src.dataKey = 'id';
 * sel.dst.dataKey = 'id';
 * sel.src.title = '可选';
 * sel.dst.title = '已选';
 *
 * sel.src.add({id: 1, name: 'Tom', text: 'Tomcat'});
 * sel.src.add({id: 2, name: 'Jerry', text: 'Jerrimy'});
 * sel.addAction = function(selectKeys){alert(selectKeys)}
 * sel.deleteAction = function(selectKeys){alert(selectKeys)}
 * sel.render();
 * </code>
 *
 * @param string id: HTML节点的id.
 */
function SelectorView(id){
	this.id = id;
	this.title = '';
	this.container = null;
	this._rendered = false;
	/**add by lingpeiyong  start**/
	/**
	 * 添加到已选框触发的事件
	 * @param string selectKeys :添加到已选框的选项的id，以英文逗号隔开
	 */
	this.addAction = function(selectKeys){};	
	/**
	 * 移除已选框触发的事件
	 * @param string selectKeys :移除已选框的选项的id，以英文逗号隔开
	 */
	this.deleteAction = function(selectKeys){};
	/**add by lingpeiyong  end**/

	// 为了在函数中引用.
	var self = this;

	var div = document.getElementById(this.id);
	div.view = this;
	this.container = div;

	var id_prefix = 'asdfsafokmlv';
	var src_id = this.id + '_' + id_prefix + '_src';
	var dst_id = this.id + '_' + id_prefix + '_dst';
	var str = '';
	str += '<table class="selector_table">';
	str += '<tr>';
	str += '<td valign="top" class="src">';
		str += '<div id="' + src_id + '" class="src_div"></div>';
	str += "</td>\n";
	str += '<td valign="top" class="exchange_button">';
		str += '<input class="select_button" type="button" value="" onclick="document.getElementById(\'' + this.id + '\').view.select()" />';
		str += '<input class="unselect_button" type="button" value="" onclick="document.getElementById(\'' + this.id + '\').view.unselect()" />';
	str += "</td>\n";
	str += '<td valign="top" class="dst">';
		str += '<div id="' + dst_id + '" class="dst_div"></div>';		
	str += "</td>\n";
	str += "</tr>\n";
	str += "</table>\n";
	this.container.innerHTML = str;

	this.src = new TableView(src_id);
	this.dst = new TableView(dst_id);

	// 重写数据表格的行双击方法.
	this.src.dblclick = function(id){
		var rows = self.src.rows;
		/*** add by lingpeyong start**/
		self.addAction(rows[id][self.src.dataKey]);		
		/*** add by lingpeyong end**/
		self.dst.add(rows[id]);
		self.src.del(rows[id]);
	};

	// 重写数据表格的行双击方法.
	this.dst.dblclick = function(id){
		var rows = self.dst.rows;
		/*** add by lingpeyong start**/
		self.deleteAction(rows[id][self.dst.dataKey]);		
		/*** add by lingpeyong end**/
		self.src.add(rows[id]);
		self.dst.del(rows[id]);
	};

	/**
	 * 渲染整个选择控件.
	 */
	this.render = function(){
		this.src.render();
		this.dst.render();
		this._rendered = true;
	};

	/**
	 * 将备选框中已选的数据移动到已选框中.
	 */
	this.select = function(){
		var rows = this.src.getSelected();
		/*** add by lingpeyong start**/
		self.addAction(this.src.getSelectedKeys().join(","));		
		/*** add by lingpeyong end**/
		this.dst.addRange(rows);
		this.src.delRange(rows);
	};

	/**
	 * 将已选框中已选的数据移动到备选框中.
	 */
	this.unselect = function(){
		var rows = this.dst.getSelected();
		/*** add by lingpeyong start**/
		self.deleteAction(this.dst.getSelectedKeys().join(","));		
		/*** add by lingpeyong end**/
		this.src.addRange(rows);
		this.dst.delRange(rows);
	};

	/**
	 * 获取已选择的的数据对象列表.
	 */
	this.getSelected = function(){
		return this.dst.rows;
	};

	/**
	 * 获取所有已选择的数据对象键值列表.
	 */
	this.getSelectedKeys = function(){
		var keys = [];
		var rows = this.dst.rows;
		for(var i in rows){
			keys.push(rows[i][this.dst.dataKey]);
		}
		return keys;
	};
}

