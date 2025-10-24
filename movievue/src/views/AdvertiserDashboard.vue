<template>
  <div class="workspace">
    <header class="workspace-header advertiser">
      <div>
        <h1>广告主工作台</h1>
        <p>企业认证 → 充值 → 建计划/素材 → 审核 → 上线投放 → 数据看板/调整/结算</p>
        <p v-if="account">账户状态：{{ account.status }} · 余额：¥{{ (account.balanceCents / 100).toFixed(2) }}</p>
      </div>
      <div class="workspace-actions">
        <button type="button" class="btn" @click="loadDashboard" :disabled="!account">刷新数据看板</button>
        <button type="button" class="btn btn-secondary" @click="backToLogin">切换账号</button>
      </div>
    </header>

    <section class="workspace-section" v-if="!account">
      <h2>第一步：提交企业认证</h2>
      <form class="workspace-form vertical" @submit.prevent="submitAccount">
        <label>
          企业名称
          <input v-model="applyForm.companyName" type="text" required placeholder="请输入营业执照名称" />
        </label>
        <button type="submit" class="btn">提交认证申请</button>
      </form>
      <p class="workspace-hint">提交后请等待管理员审核，审核通过即可继续充值与建计划。</p>
    </section>

    <template v-else>
      <section class="workspace-section">
        <h2>第二步：账户充值</h2>
        <form class="workspace-form" @submit.prevent="submitRecharge">
          <label>
            充值金额（元）
            <input v-model.number="rechargeForm.amount" type="number" min="1" required />
          </label>
          <button type="submit" class="btn">确认充值</button>
        </form>
        <p class="workspace-meta">提示：充值实时生效，余额用于计划消耗与结算。</p>
      </section>

      <section class="workspace-section">
        <h2>第三步：创建广告计划</h2>
        <form class="workspace-form" @submit.prevent="submitCampaign">
          <label>
            计划名称
            <input v-model="campaignForm.name" type="text" required />
          </label>
          <label>
            投放目标
            <input v-model="campaignForm.objective" type="text" placeholder="品牌曝光" />
          </label>
          <label>
            总预算（元）
            <input v-model.number="campaignForm.total" type="number" min="1" required />
          </label>
          <label>
            日预算（元）
            <input v-model.number="campaignForm.daily" type="number" min="1" required />
          </label>
          <label>
            出价模式
            <select v-model="campaignForm.bidType">
              <option value="CPM">CPM</option>
              <option value="CPC">CPC</option>
            </select>
          </label>
          <label>
            起始时间
            <input v-model="campaignForm.start" type="datetime-local" required />
          </label>
          <label>
            结束时间
            <input v-model="campaignForm.end" type="datetime-local" />
          </label>
          <label>
            投放节奏
            <select v-model="campaignForm.pacing">
              <option value="EVEN">均匀投放</option>
              <option value="ASAP">尽快投放</option>
            </select>
          </label>
          <button type="submit" class="btn">创建计划</button>
        </form>
        <div class="workspace-subsection">
          <h3>已创建计划</h3>
          <table class="workspace-table" v-if="campaigns.length">
            <thead>
              <tr>
                <th>名称</th>
                <th>目标</th>
                <th>预算（元）</th>
                <th>状态</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in campaigns" :key="item.id">
                <td>{{ item.name }}</td>
                <td>{{ item.objective || '—' }}</td>
                <td>{{ (item.budgetTotalCents / 100).toFixed(2) }}</td>
                <td>{{ item.status }}</td>
              </tr>
            </tbody>
          </table>
          <p v-else class="workspace-empty">暂无计划，先完成以上表单创建。</p>
        </div>
      </section>

      <section class="workspace-section">
        <h2>第四步：上传广告素材并提交审核</h2>
        <form class="workspace-form" @submit.prevent="submitCreativeForm">
          <label>
            所属计划
            <select v-model.number="creativeForm.campaignId" required>
              <option disabled value="">请选择</option>
              <option v-for="item in campaigns" :key="item.id" :value="item.id">{{ item.name }}</option>
            </select>
          </label>
          <label>
            类型
            <select v-model="creativeForm.type">
              <option value="IMG">图片</option>
              <option value="VIDEO">视频</option>
            </select>
          </label>
          <label>
            素材地址
            <input v-model="creativeForm.assetUrl" type="url" required placeholder="https://example.com/banner.png" />
          </label>
          <label>
            点击地址
            <input v-model="creativeForm.clickUrl" type="url" placeholder="https://landing.page" />
          </label>
          <button type="submit" class="btn">提交素材</button>
        </form>
        <div class="workspace-subsection">
          <h3>素材审核列表</h3>
          <table class="workspace-table" v-if="creatives.length">
            <thead>
              <tr>
                <th>ID</th>
                <th>类型</th>
                <th>状态</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in creatives" :key="item.id">
                <td>{{ item.id }}</td>
                <td>{{ item.type }}</td>
                <td>{{ item.status }}</td>
                <td class="workspace-table-actions">
                  <button class="btn btn-secondary" @click="requestReview(item)" :disabled="item.status === 'APPROVED' || item.status === 'PENDING'">提交审核</button>
                  <button class="btn" @click="activate(item)" :disabled="item.status !== 'APPROVED'">上线投放</button>
                </td>
              </tr>
            </tbody>
          </table>
          <p v-else class="workspace-empty">尚未上传素材，完成计划创建后即可上传。</p>
        </div>
      </section>

      <section class="workspace-section">
        <h2>第五步：数据看板 / 调整 / 结算</h2>
        <div v-if="dashboard" class="workspace-dashboard">
          <div class="workspace-dashboard-card">
            <h3>曝光</h3>
            <p>{{ dashboard.impressionCount }}</p>
          </div>
          <div class="workspace-dashboard-card">
            <h3>点击</h3>
            <p>{{ dashboard.clickCount }}</p>
          </div>
          <div class="workspace-dashboard-card">
            <h3>计划状态</h3>
            <ul>
              <li v-for="(count, key) in dashboard.campaignStatus" :key="key">{{ key }}：{{ count }}</li>
            </ul>
          </div>
          <div class="workspace-dashboard-card">
            <h3>素材状态</h3>
            <ul>
              <li v-for="(count, key) in dashboard.creativeStatus" :key="key">{{ key }}：{{ count }}</li>
            </ul>
          </div>
        </div>
        <form class="workspace-form" @submit.prevent="settle">
          <label>
            结算金额（元）
            <input v-model.number="settlementForm.amount" type="number" min="0" />
          </label>
          <button type="submit" class="btn">结算扣款</button>
        </form>
      </section>
    </template>

    <p v-if="message" class="workspace-hint">{{ message }}</p>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import {
  applyAdAccount,
  fetchAdAccountByUser,
  rechargeAdAccount,
  createCampaign,
  listCampaigns,
  createCreative,
  listCreatives,
  submitCreative as submitCreativeReview,
  activateCreative,
  fetchAdDashboard,
  settleAdAccount
} from '../services/api';

const router = useRouter();
const stored = localStorage.getItem('jmk_movie_user');
const currentUser = stored ? JSON.parse(stored) : null;

const account = ref(null);
const applyForm = reactive({ companyName: '' });
const rechargeForm = reactive({ amount: 1000 });
const campaignForm = reactive({
  name: '',
  objective: '',
  total: 10000,
  daily: 2000,
  bidType: 'CPM',
  start: new Date().toISOString().slice(0, 16),
  end: '',
  pacing: 'EVEN'
});
const creativeForm = reactive({ campaignId: '', type: 'IMG', assetUrl: '', clickUrl: '' });
const campaigns = ref([]);
const creatives = ref([]);
const dashboard = ref(null);
const settlementForm = reactive({ amount: 0 });
const message = ref('');

function ensureAdvertiser() {
  if (!currentUser || currentUser.role !== 'ADVERTISER') {
    router.push('/login');
    throw new Error('无权限');
  }
}

async function loadAccount() {
  ensureAdvertiser();
  try {
    account.value = await fetchAdAccountByUser(currentUser.id);
    if (account.value) {
      await Promise.all([loadCampaigns(), loadCreatives(), loadDashboard()]);
    }
  } catch (error) {
    console.error(error);
  }
}

async function submitAccount() {
  ensureAdvertiser();
  try {
    account.value = await applyAdAccount({ userId: currentUser.id, companyName: applyForm.companyName });
    message.value = '提交成功，等待管理员审核。';
  } catch (error) {
    message.value = error.message || '提交失败';
  }
}

async function submitRecharge() {
  ensureAdvertiser();
  try {
    const cents = Math.round(rechargeForm.amount * 100);
    account.value = await rechargeAdAccount(account.value.id, { amountCents: cents });
    message.value = '充值成功。';
  } catch (error) {
    message.value = error.message || '充值失败';
  }
}

function toIso(value) {
  return value ? new Date(value).toISOString() : null;
}

async function submitCampaign() {
  ensureAdvertiser();
  try {
    const payload = {
      accountId: account.value.id,
      name: campaignForm.name,
      objective: campaignForm.objective,
      budgetTotalCents: Math.round(campaignForm.total * 100),
      budgetDailyCents: Math.round(campaignForm.daily * 100),
      bidType: campaignForm.bidType,
      startTime: toIso(campaignForm.start),
      endTime: campaignForm.end ? toIso(campaignForm.end) : null,
      pacing: campaignForm.pacing
    };
    await createCampaign(payload);
    message.value = '计划已创建，等待素材投放。';
    await loadCampaigns();
  } catch (error) {
    message.value = error.message || '创建计划失败';
  }
}

async function submitCreativeForm() {
  ensureAdvertiser();
  try {
    await createCreative({
      campaignId: creativeForm.campaignId,
      type: creativeForm.type,
      assetUrl: creativeForm.assetUrl,
      clickUrl: creativeForm.clickUrl || undefined
    });
    message.value = '素材上传成功，请提交审核。';
    creativeForm.assetUrl = '';
    creativeForm.clickUrl = '';
    await loadCreatives();
  } catch (error) {
    message.value = error.message || '上传素材失败';
  }
}

async function requestReview(item) {
  ensureAdvertiser();
  try {
    await submitCreativeReview(item.id);
    message.value = '素材已提交审核。';
    await loadCreatives();
  } catch (error) {
    message.value = error.message || '提交审核失败';
  }
}

async function activate(item) {
  ensureAdvertiser();
  try {
    await activateCreative(item.id);
    message.value = '素材已上线投放。';
    await loadCreatives();
  } catch (error) {
    message.value = error.message || '上线失败';
  }
}

async function loadCampaigns() {
  if (!account.value) return;
  campaigns.value = await listCampaigns(account.value.id);
}

async function loadCreatives() {
  if (!account.value) return;
  creatives.value = await listCreatives(account.value.id);
}

async function loadDashboard() {
  if (!account.value) return;
  dashboard.value = await fetchAdDashboard(account.value.id);
}

async function settle() {
  if (!account.value) return;
  ensureAdvertiser();
  try {
    const cents = Math.round((settlementForm.amount || 0) * 100);
    account.value = await settleAdAccount(account.value.id, { amountCents: cents });
    message.value = '结算成功。';
  } catch (error) {
    message.value = error.message || '结算失败';
  }
}

function backToLogin() {
  router.push('/login');
}

onMounted(loadAccount);
</script>
