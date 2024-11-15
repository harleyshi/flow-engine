import type { Menu } from "@/data/menuList";
import { loadMenuRouter } from "@/router/loadMenuRouter";
import { defineStore } from "pinia";
import { menuList } from "../data/menuList";
import router from "../router";

export type Tag = {
  path: string;
  name: string;
  title: string;
};

export declare interface Store {
  activePath: string;
  tagsList: Tag[];
  meunIsCollapsed: boolean;
  loginUser: string | null;
  menuList: Menu[];
}

export const userStore = defineStore({
  id: "counter",
  state: (): Store => {
    //刷新后，重新加载缓存中的页签
    let tagsList: Tag[] = [];
    const tagStr = sessionStorage.getItem("store_tagsList");
    if (tagStr && tagStr != "") {
      tagsList = JSON.parse(tagStr);
    }

    //刷新后，重新加载缓存中的页签
    const loginUser = sessionStorage.getItem("store_loginUser");

    //刷新后，重新加载缓存中的动态路由
    let menuList: Menu[] = [];
    const menuStr = sessionStorage.getItem("store_menuList");
    if (menuStr && menuStr != "") {
      menuList = JSON.parse(menuStr);
      // loadMenuRouter(router, menuList);
    }

    return {
      activePath: "",
      tagsList: tagsList,
      meunIsCollapsed: false,
      loginUser: loginUser,
      menuList: menuList,
    };
  },
  getters: {
  },
  actions: {
    setMenuList(value: Array<Menu>) {
      this.menuList = value;
      sessionStorage.setItem("store_menuList", JSON.stringify(this.menuList));
    },
    loadMenu() {
      const list = menuList;
      this.clearAllTags();
      this.setMenuList(list);
      loadMenuRouter(router, list);
    },
    setLoginUser(value: string) {
      this.loginUser = value;
      sessionStorage.setItem("store_loginUser", value);
    },
    setMeunIsCollapsed(value: boolean) {
      this.meunIsCollapsed = value;
    },
    setTagsItem(tagsList: Tag[]) {
      this.tagsList = tagsList;
      sessionStorage.setItem("store_tagsList", JSON.stringify(this.tagsList));
    },
    delTagsItem(index: number) {
      if (this.tagsList && this.tagsList.length > 0) {
        this.tagsList.splice(index, 1);
        this.setTagsItem(this.tagsList);
      }
    },
    addTagsItem(tag: Tag) {
      if (!this.tagsList) {
        this.tagsList = [];
      }
      this.tagsList.push(tag);
      this.setTagsItem(this.tagsList);
    },
    updateTagsItem(tag: Tag) {
      const index = this.tagsList.findIndex((item) => tag.path === item.path);
      if (index >= 0) {
        this.tagsList[index] = tag;
        this.setTagsItem(this.tagsList);
      } else {
        this.addTagsItem(tag);
      }
    },
    clearAllTags() {
      this.setTagsItem([]);
    },
    closeTagsOther(tagsList: Tag[]) {
      this.setTagsItem(tagsList);
    },
    // 关闭指定tab页签
    closePage(path?: string) {
      if (!path) {
        path = this.activePath;
      }
      // 删除当前页面
      const index = this.tagsList.findIndex((item) => item.path === path);
      index >= 0 && this.delTagsItem(index);
      // 设置下一页面
      const nextTag = this.tagsList[index] ? this.tagsList[index] : this.tagsList[index - 1];
      this.activePath = nextTag && nextTag.path ? nextTag.path : "/home";
    },
    // 关闭当前打开指定页面
    closeOpenPage(toPath: string, path?: string) {
      if (!path) {
        path = this.activePath;
      }
      // 删除当前页面
      const index = this.tagsList.findIndex((item) => item.path === path);
      index >= 0 && this.delTagsItem(index);
      // 设置下一页面
      this.activePath = toPath;
    },
    isTagsRouter(path: string) {
      if (path === "/login") {
        return false;
      } else {
        return true;
      }
    },
  },
});
